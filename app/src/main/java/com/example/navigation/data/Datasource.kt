package com.example.navigation.data


import com.example.navigation.R
import com.example.navigation.model.Affirmation

/**
 * [Datasource] generates a list of [Affirmation]xsz
 */
class Datasource() {

    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.a),
            Affirmation(R.string.affirmation2, R.drawable.b),
            Affirmation(R.string.affirmation3, R.drawable.c),
            Affirmation(R.string.affirmation4, R.drawable.d),
            Affirmation(R.string.affirmation5, R.drawable.e),
            Affirmation(R.string.affirmation6, R.drawable.f),
            Affirmation(R.string.affirmation7, R.drawable.g),
            Affirmation(R.string.affirmation8, R.drawable.h),
            Affirmation(R.string.affirmation9, R.drawable.i),
            Affirmation(R.string.affirmation10, R.drawable.j
            ))
    }
}