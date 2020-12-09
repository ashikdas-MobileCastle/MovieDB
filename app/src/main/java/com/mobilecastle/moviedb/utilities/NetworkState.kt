package de.mobilecastle.moviedb.utilities

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

//With type val can not be changeable
//With type var is a mutable variable which can be changed later
class NetworkState(val status: Status, val msg: String) {

    //companion used when you want something to be static
    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val EOL: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.FAILED, "Something not right!!!")
            EOL = NetworkState(Status.FAILED, "You have reached the end")
        }
    }
}