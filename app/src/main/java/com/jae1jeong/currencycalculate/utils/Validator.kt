package com.jae1jeong.currencycalculate.utils

object Validator {

    /**
     * 숫자만 추출
     */
    fun onlyNumberExceptDot(text: String): Double {
        //onlyNumberExceptDot
        return text.replace("[^0-9.]".toRegex(), "").toDouble()
    }

    /**
     * 0~10,000 범위 내의 숫자인지 체크
     */
    fun checkValidInRange(amount: Double): Boolean {
        return amount in 0.00..10_000.00
    }

}