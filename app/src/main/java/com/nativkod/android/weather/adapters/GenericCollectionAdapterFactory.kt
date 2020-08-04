package com.nativkod.android.weather.adapters

import com.nativkod.android.weather.models.CurrentWeather
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class GenericCollectionAdapterFactory<TCollection : MutableCollection<*>>(
    private val collectionClazz: Class<TCollection>,
    private val createEmptyCollection: () -> MutableCollection<Any>
) : JsonAdapter.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        return when (type) {
            Types.newParameterizedType(ArrayList::class.java, CurrentWeather::class.java) ->
                moshi.adapter<ArrayList<CurrentWeather>>(type)
            else -> null
        }
    }
}