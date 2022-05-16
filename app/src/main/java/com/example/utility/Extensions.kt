package com.example.utility

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

 inline fun <reified T> CollectionReference.listenDocuments() =
    run {
        val collectionReference = this
        callbackFlow {
            val snapshotListener = collectionReference.addSnapshotListener() { value, error ->
                val response = if (error == null) {
                    if (value != null) {
                        val documents = value.documents.map { document ->
                            document.toObject<T>()
                        }
                        State.success(documents)
                    } else {
                        State.failed("Documents not found")
                    }
                } else {
                    close(error)
                    State.failed(error.message.toString())
                }
                try {
                    trySend(response)
                } catch (e: Throwable) {
                    close(e)
                }
            }
            awaitClose {
                Log.d(TAG, "Get Documents has been removed from lifecycle of composable")
                snapshotListener.remove()
            }
        }.catch {
            // If exception is thrown, emit failed state along with message.
            emit(State.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

suspend inline fun <reified T> Query.listenQuery() =
    run {
        val queryReference = this
        callbackFlow {
            val snapshotListener = queryReference.addSnapshotListener() { value, error ->
                val response = if (error == null) {
                    if (value != null) {
                        val documents = value.documents.map { document ->
                            document.toObject<T>()
                        }
                        State.success(documents)
                    } else {
                        State.failed("Documents not found")
                    }
                } else {
                    close(error)
                    State.failed(error.message.toString())
                }
                try {
                    trySend(response)
                } catch (e: Throwable) {
                    close(e)
                }
            }
            awaitClose {
                Log.d(TAG, "Get Documents has been removed from lifecycle of composable")
                snapshotListener.remove()
            }
        }.catch {
            // If exception is thrown, emit failed state along with message.
            emit(State.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }


suspend inline fun <reified T> DocumentReference.listenDocument() =
    run {
        val documentReference = this
        callbackFlow {
            val snapshotListener = documentReference.addSnapshotListener() { value, error ->
                val response = if (error == null) {
                    if (value != null && value.exists()) {
                        State.success(value.toObject<T>())
                    } else {
                        State.failed("Document not found")
                    }
                } else {
                    close(error)
                    State.failed(error.message.toString())
                }
                try {
                    trySend(response)
                } catch (e: Throwable) {
                    close(e)
                }
            }
            awaitClose {
                Log.d(TAG, "Get Document has been removed from lifecycle of composable")
                snapshotListener.remove()
            }
        }.catch {
            // If exception is thrown, emit failed state along with message.
            emit(State.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
