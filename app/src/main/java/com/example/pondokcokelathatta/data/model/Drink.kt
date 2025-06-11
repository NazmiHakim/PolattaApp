package com.example.pondokcokelathatta.data.model

import androidx.annotation.DrawableRes

/**
 * Kelas data untuk merepresentasikan item minuman dalam menu.
 *
 * @property name Nama minuman.
 * @property imageRes ID resource gambar untuk minuman.
 */
data class Drink(
    val name: String,
    @DrawableRes val imageRes: Int
)
