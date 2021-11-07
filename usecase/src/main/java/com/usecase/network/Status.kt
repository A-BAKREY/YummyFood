package com.usecase.network

import androidx.annotation.IntDef

/**

 */

@IntDef(LOADING, SUCCESS, ERROR,ERRORNet,LogIn)
@Retention(AnnotationRetention.SOURCE)
annotation class Status

const val LOADING = 0
const val SUCCESS = 1
const val ERROR = 2
const val ERRORNet =3
const val LogIn =4
const val serverMsg =5
