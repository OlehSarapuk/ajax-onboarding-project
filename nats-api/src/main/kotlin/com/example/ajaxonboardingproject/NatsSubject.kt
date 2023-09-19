package com.example.ajaxonboardingproject

object NatsSubject {
    const val ADD_NEW_CINEMA_HALL_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.cinema_hall.add"
    const val FIND_ALL_CINEMA_HALLS_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.cinema_hall.find_all"
    const val ADD_NEW_MOVIE_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.movie.add"
    const val FIND_ALL_MOVIES_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.movie.find_all"
    const val ADD_NEW_MOVIE_SESSION_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.movie_session.add"
    const val UPDATE_MOVIE_SESSION_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.movie_session.update"
    const val DELETE_MOVIE_SESSION_SUBJECT = "v2.ajax_onboarding_project.input.reqreply.movie_session.delete"
    const val KAFKA_GET_FRESHLY_ADDED_CINEMA_HALL_SUBJECT = "v2.ajax_onboarding_project.input.pubsub.cinema_hall.get_freshly_added"
}
