package com.example.data.repository

import android.util.Log
import com.example.domain.model.City
import com.example.domain.model.UserProfile
import com.example.domain.repository.UserRepository
import com.example.utility.State
import com.example.utility.TAG
import com.example.utility.listenDocument
import com.example.utility.listenDocuments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(val db: FirebaseFirestore, val auth: FirebaseAuth) : UserRepository {


    //private val db = firebase.firestore
    //private val auth = firebase.auth

    suspend fun addCity() {
        val data = City(
            name = "Aydın", country = "Turkey", isCapital = false,
            population = 5_500_000
        )

        db.collection("cities")
            .add(data)
            .await()
    }

    fun addDifferentTypes() {
        val city = City(
            name = "Los Angeles", state = "CA", country = "USA",
            isCapital = false, population = 5000000L, regions = listOf("west_coast", "socal")
        )
        db.collection("cities").document("LA").set(city)
    }

    fun transitionUpdate() {
        val sfDocRef = db.collection("cities").document("LA")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            val newPopulation = snapshot.getDouble("population")!! + 10
            transaction.update(sfDocRef, "population", newPopulation)

            // Success
            newPopulation
        }.addOnSuccessListener { result ->
            Log.d(
                TAG, "Transaction success and the result is $result"
            )
        }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

        // This example for Batch operations
//        val nycRef = db.collection("cities").document("NYC")
//        val sfRef = db.collection("cities").document("LA")
//        val laRef = db.collection("cities")
//            .document("vzjaz1SMYOBi5tMCyV7u")
//
//// Get a new write batch and commit all write operations
//        db.runBatch { batch ->
//            // Set the value of 'NYC'
//            batch.set(nycRef, City(name = "New York City", country = "USA", population
//            = 1_000_000))
//
//            // Update the population of 'SF'
//            batch.update(sfRef, "population", 1_000_000L)
//
//            // Delete the city 'LA'
//            batch.delete(laRef)
//        }.addOnSuccessListener {
//           Log.d(TAG,"Run Batch have completed")
//        }.addOnFailureListener {
//            Log.d(TAG,"Run Batch have failed")
//        }

    }


    fun updateCity() {
        val washingtonRef =
            db.collection("cities")
                .document("SF")

        val updates = hashMapOf<String, Any>(
            "timestamp" to FieldValue.serverTimestamp(),
            "population" to FieldValue.increment(10)
        )
// Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update(updates)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    fun updateServerTimeStamp() {
        // If you're using custom Kotlin objects in Android, add an @ServerTimestamp
// annotation to a Date field for your custom object classes. This indicates
// that the Date field should be treated as a server timestamp by the object mapper.
        val docRef = db.collection("cities").document("vzjaz1SMYOBi5tMCyV7u")


// Update the timestamp field with the value from the server
        val updates = hashMapOf<String, Any>(
            "timestamp" to FieldValue.serverTimestamp(),
            "population" to 9_500_000
        )
        docRef.update(updates)
    }

    suspend fun deleteDocument() {
        db.collection("cities").document("B4QLBrwjczs74QKNO5Wg")
            .delete().await()
    }
    // GET OPERATIONS

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getDocuments() = db.collection("cities").listenDocuments<City>()


    suspend fun getDocument() = flow {
        val docRef = db.collection("cities").document("SF")
        emit(State.loading())

        val data = docRef.get().await()
        if (data.exists()) {
            val convertedData = data.toObject<City>()
            emit(State.success(convertedData))
        } else {
            emit(State.failed("No document found"))
        }
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getCachedData() {
        val docRef = db.collection("cities").document("SF")

        // Source can be CACHE, SERVER, or DEFAULT.
        val source = Source.CACHE

        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                Log.d(TAG, "Cached document data: ${document?.data}")
            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }

    fun getMultipleDocument() {
        db.collection("cities")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }


    fun snapshotListener() {
        val docRef = db.collection("cities").document("SF")
        docRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "$source data: ${snapshot.data}")
            } else {
                Log.d(TAG, "$source data: null")
            }
        }
    }

    suspend fun simpleQueries() {
        // Create a reference to the cities collection
        val citiesRef = db.collection("cities").whereNotEqualTo("capital", true)
        try {
            val documents = citiesRef.get().await()
            for (document in documents) {
                Log.d(TAG, "${document.data}")
            }
        } catch (e: Throwable) {
            Log.d(TAG, e.message.toString())
        }

        // Create a query against the collection.
        //val query = citiesRef.whereNotEqualTo("capital", true)
        /*val query = citiesRef.whereArrayContainsAny("regions", listOf("west_coast", " +
            ""east_coast"))*/
//        try {
//            val documents = query.get().await()
//            Log.d(TAG,"documents ${documents.size()}")
//            if (!documents.isEmpty) {
//                for (document in documents) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            } else {
//                Log.d(TAG, "There is no document")
//            }
//
//        } catch (e: Throwable) {
//            Log.d(TAG, "$e")
//        }
        //val citiesRef = db.collection("cities")

        val query = citiesRef.whereArrayContains("regions", "west_coast")
        try {
            val documents = query.get().await()
            if (documents.isEmpty) {
                Log.d(TAG, "There is no document")
            } else {
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
        } catch (e: Throwable) {
            Log.d(TAG, e.message.toString())
        }

    }

    suspend fun compositeQueries() {
        val citiesRef = db.collection("cities")
        val documents =
            citiesRef.whereIn("state", listOf("CA"))
                .whereGreaterThan("population", 860110)

        //citiesRef.whereEqualTo("state", "CA").whereLessThan("population", 1_000_000)
        try {
            val dataSet = documents.get().await()
            for (data in dataSet) {
                Log.d(TAG, "Data = ${data.data}")
            }
        } catch (e: Throwable) {
            Log.d(TAG, e.message.toString())
        }
    }

    suspend fun addLandmarks() {
        val documents =
            db
                .collectionGroup("landmarks")
                .whereEqualTo("type", "museum")
                .get()
                .await()
        for (document in documents) {
            Log.d(TAG, document.data.toString())
        }

    }

    suspend fun getOrderedResult() {
        val citiesRef = db.collection("cities")
//        val documents =
//            citiesRef.orderBy("state").orderBy(
//                "population", Query.Direction
//                    .DESCENDING
//            ).get().await()
        val documents =
            citiesRef.whereGreaterThan("population", 100_000).orderBy("population")
                .limit(2)
        convertCity(documents)
    }

    private suspend fun convertCity(documents: Query) {
        val dataSet = documents.get().await()
        if (dataSet.size() == 0) Log.d(TAG, "There is no document")
        for (document in dataSet) {
            val city = document.toObject<City>()
            Log.d(
                TAG, "Name of the city is ${city.name} and state is ${city.state} and " +
                        "population of city is ${city.population}"
            )
        }
    }

    suspend fun paginateData() {

        // Get the data for "San Francisco"
//       val documentSnapshot =  db.collection("cities").document("SF")
//            .get()
//            .await()
//        // Get all cities with a population bigger than San Francisco.
//        val biggerThanSf = db.collection("cities")
//            .orderBy("population")
//            .startAt(documentSnapshot)
//        convertCity(biggerThanSf)

//        //Paginate data
//        // Construct query for first 25 cities, ordered by population
//        val first = db.collection("cities")
//            .orderBy("population")
//            .limit(25)
//
//        val documents = first.get().await()
//
//        // Get the last visible document
//        val lastVisible = documents.documents.last()
//        // Construct a new query starting at this document,
//        // get the next 25 cities.
//        val next = db.collection("cities")
//            .orderBy("population")
//            .startAfter(lastVisible)
//            .limit(25)
//        convertCity(next)


        // Will return all Springfields
//        val documents = db.collection("cities")
//            .orderBy("name")
//            .orderBy("state")
//            .startAt("Springfield")


// Will return "Springfield, Missouri" and "Springfield, Wisconsin"
        val documents = db.collection("cities")
            .orderBy("name")
            .orderBy("state")
            .startAt("Springfield", "Missouri")
        convertCity(documents)
    }

    suspend fun getOfflineData() {

        db.collection("cities").whereEqualTo("state", "CA")
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen error", e)
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d(TAG, "New city: ${change.document.data}")
                    }

                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                    Log.d(TAG, "Data fetched from $source")
                }
            }
    }

    override suspend fun getUsers(): Flow<State<List<UserProfile?>>> {
        return db.collection("users").listenDocuments<UserProfile>()
    }

    override suspend fun getUser(): Flow<State<UserProfile?>> {
        val userId = currentUserId()
        return db.collection("users").document(userId).listenDocument()
    }

    private fun currentUserId(): String {
        return auth.currentUser?.uid ?: "no User"
    }


}
