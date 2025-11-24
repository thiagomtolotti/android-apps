package br.ufpr.multiactivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userName: String,
    val n1Peso: Int,
    val n1: Double
) : Parcelable
