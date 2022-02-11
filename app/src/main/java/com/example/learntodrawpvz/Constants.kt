package com.example.learntodrawpvz

import android.content.Context
import com.example.learntodrawpvz.entities.PvZModel

class Constants {
    companion object {
        private fun getImageList(index: Int): ArrayList<String> {
            when (index) {
                1 -> return d01Image
                2 -> return d02Image
                3 -> return d03Image
                4 -> return d04Image
                5 -> return d05Image
                6 -> return d06Image
                7 -> return d07Image
                8 -> return d08Image
                9 -> return d09Image
                10 -> return d10Image
                11 -> return d11Image
                12 -> return d12Image
                13 -> return d13Image
                14 -> return d14Image
                15 -> return d15Image
                16 -> return d16Image
                17 -> return d17Image
                18 -> return d18Image
                19 -> return d19Image
                20 -> return d20Image
                21 -> return d21Image
                22 -> return d22Image
                23 -> return d23Image
                24 -> return d24Image
                25 -> return d25Image
                26 -> return d26Image
                27 -> return d27Image
                28 -> return d28Image
            }
            return d01Image
        }

        fun getPvZ(index: Int): PvZModel {
            return PvZModel(
                getName(index, ProjectApplication.appContext),
                getDifficulty(index),
                getAvatar(index),
                getImageList(index),
                getImageList(index).size
            )
        }

        private fun getDifficulty(index: Int): String {
            when (index) {
                1 -> return "diff_2"
                2 -> return "diff_2"
                3 -> return "diff_4"
                4 -> return "diff_1"
                5 -> return "diff_2"
                6 -> return "diff_3"
                7 -> return "diff_2"
                8 -> return "diff_1"
                9 -> return "diff_2"
                10 -> return "diff_4"
                11 -> return "diff_1"
                12 -> return "diff_2"
                13 -> return "diff_3"
                14 -> return "diff_5"
                15 -> return "diff_3"
                16 -> return "diff_3"
                17 -> return "diff_5"
                18 -> return "diff_4"
                19 -> return "diff_5"
                20 -> return "diff_4"
                21 -> return "diff_1"
                22 -> return "diff_3"
                23 -> return "diff_2"
                24 -> return "diff_3"
                25 -> return "diff_4"
                26 -> return "diff_3"
                27 -> return "diff_5"
                28 -> return "diff_1"
            }
            return "diff_2"
        }

        private fun getAvatar(index: Int): String {
            when (index) {
                1 -> return "d01"
                2 -> return "d02"
                3 -> return "d03"
                4 -> return "d04"
                5 -> return "d05"
                6 -> return "d06"
                7 -> return "d07"
                8 -> return "d08"
                9 -> return "d09"
                10 -> return "d10"
                11 -> return "d11"
                12 -> return "d12"
                13 -> return "d13"
                14 -> return "d14"
                15 -> return "d15"
                16 -> return "d16"
                17 -> return "d17"
                18 -> return "d18"
                19 -> return "d19"
                20 -> return "d20"
                21 -> return "d21"
                22 -> return "d22"
                23 -> return "d23"
                24 -> return "d24"
                25 -> return "d25"
                26 -> return "d26"
                27 -> return "d27"
                28 -> return "d28"
            }
            return "n01"
        }

        private fun getName(index: Int, context: Context): String {
            when (index) {
                1 -> return context.getString(R.string.n01)
                2 -> return context.getString(R.string.n02)
                3 -> return context.getString(R.string.n04)
                4 -> return context.getString(R.string.n04)
                5 -> return context.getString(R.string.n05)
                6 -> return context.getString(R.string.n06)
                7 -> return context.getString(R.string.n07)
                8 -> return context.getString(R.string.n08)
                9 -> return context.getString(R.string.n09)
                10 -> return context.getString(R.string.n10)
                11 -> return context.getString(R.string.n11)
                12 -> return context.getString(R.string.n12)
                13 -> return context.getString(R.string.n13)
                14 -> return context.getString(R.string.n14)
                15 -> return context.getString(R.string.n15)
                16 -> return context.getString(R.string.n16)
                17 -> return context.getString(R.string.n17)
                18 -> return context.getString(R.string.n18)
                19 -> return context.getString(R.string.n19)
                20 -> return context.getString(R.string.n20)
                21 -> return context.getString(R.string.n21)
                22 -> return context.getString(R.string.n22)
                23 -> return context.getString(R.string.n23)
                24 -> return context.getString(R.string.n24)
                25 -> return context.getString(R.string.n25)
                26 -> return context.getString(R.string.n26)
                27 -> return context.getString(R.string.n27)
                28 -> return context.getString(R.string.n28)
            }
            return context.getString(R.string.n01)
        }

        private val d01Image = arrayListOf(
            "d01_01",
            "d01_02",
            "d01_03",
            "d01_04",
            "d01_05",
            "d01_06",
            "d01_07",
            "d01_08",
            "d01_09",
            "d01_10",
            "d01_11"
        )
        private val d02Image = arrayListOf(
            "d02_01",
            "d02_02",
            "d02_03",
            "d02_04",
            "d02_05",
            "d02_06",
            "d02_07",
            "d02_08",
            "d02_09",
            "d02_10",
            "d02_11",
            "d02_12",
            "d02_13",
            "d02_14"
        )
        private val d03Image = arrayListOf(
            "d03_01",
            "d03_02",
            "d03_03",
            "d03_04",
            "d03_05",
            "d03_06",
            "d03_07",
            "d03_08",
            "d03_09",
            "d03_10",
            "d03_11",
            "d03_12",
            "d03_13",
            "d03_14",
            "d03_15",
            "d03_16",
            "d03_17",
            "d03_18"
        )
        private val d04Image = arrayListOf(
            "d04_01",
            "d04_02",
            "d04_03",
            "d04_04",
            "d04_05",
            "d04_06",
            "d04_07",
            "d04_08",
            "d04_09"
        )
        private val d05Image = arrayListOf(
            "d05_01",
            "d05_02",
            "d05_03",
            "d05_04",
            "d05_05",
            "d05_06",
            "d05_07",
            "d05_08",
            "d05_09",
            "d05_10",
            "d05_11",
            "d05_12",

            )
        private val d06Image = arrayListOf(
            "d06_01",
            "d06_02",
            "d06_03",
            "d06_04",
            "d06_05",
            "d06_06",
            "d06_07",
            "d06_08",
            "d06_09",
            "d06_10",
            "d06_11",
            "d06_12",
            "d06_13",
            "d06_14"
        )
        private val d07Image = arrayListOf(
            "d07_01",
            "d07_02",
            "d07_03",
            "d07_04",
            "d07_05",
            "d07_06",
            "d07_07",
            "d07_08",
            "d07_09",
            "d07_10",
            "d07_11",
            "d07_12",
            "d07_13",
            "d07_14",
            "d07_15"
        )
        private val d08Image = arrayListOf(
            "d08_01",
            "d08_02",
            "d08_03",
            "d08_04",
            "d08_05",
            "d08_06",
            "d08_07",
            "d08_08"
        )
        private val d09Image = arrayListOf(
            "d09_01",
            "d09_02",
            "d09_03",
            "d09_04",
            "d09_05",
            "d09_06",
            "d09_07",
            "d09_08",
            "d09_09",
            "d09_10",
            "d09_11",
            "d09_12",
            "d09_13",
            "d09_14",
            "d09_15",
            "d09_16"
        )
        private val d10Image = arrayListOf(
            "d10_01",
            "d10_02",
            "d10_03",
            "d10_04",
            "d10_05",
            "d10_06",
            "d10_07",
            "d10_08",
            "d10_09",
            "d10_10",
            "d10_11",
            "d10_12",
            "d10_13",
            "d10_14",
            "d10_15",
            "d10_16",
            "d10_17",
            "d10_18",
            "d10_19",
            "d10_20"
        )
        private val d11Image = arrayListOf(
            "d11_01",
            "d11_02",
            "d11_03",
            "d11_04",
            "d11_05",
            "d11_06",
            "d11_07",
            "d11_08",
            "d11_09",
        )
        private val d12Image = arrayListOf(
            "d12_01",
            "d12_02",
            "d12_03",
            "d12_04",
            "d12_05",
            "d12_06",
            "d12_07",
            "d12_08",
            "d12_09",
            "d12_10",
            "d12_11",
            "d12_12"
        )
        private val d13Image = arrayListOf(
            "d13_01",
            "d13_02",
            "d13_03",
            "d13_04",
            "d13_05",
            "d13_06",
            "d13_07",
            "d13_08",
            "d13_09",
            "d13_10",
            "d13_11",
            "d13_12",
            "d13_13",
            "d13_14"
        )
        private val d14Image = arrayListOf(
            "d14_01",
            "d14_02",
            "d14_03",
            "d14_04",
            "d14_05",
            "d14_06",
            "d14_07",
            "d14_08",
            "d14_09",
            "d14_10",
            "d14_11",
            "d14_12",
            "d14_13",
            "d14_14",
            "d14_15",
            "d14_16",
            "d14_17",
            "d14_18",
            "d14_19",
            "d14_20",
            "d14_21",
            "d14_22",
            "d14_23"
        )
        private val d15Image = arrayListOf(
            "d16_01",
            "d16_02",
            "d16_03",
            "d16_04",
            "d16_05",
            "d16_06",
            "d16_07",
            "d16_08",
            "d16_09",
            "d16_10",
            "d16_11",
            "d16_12",
            "d16_13",
            "d16_14",
            "d16_15",
            "d16_16",
        )
        private val d16Image = arrayListOf(
            "d15_01",
            "d15_02",
            "d15_03",
            "d15_04",
            "d15_05",
            "d15_06",
            "d15_07",
            "d15_08",
            "d15_09",
            "d15_10",
            "d15_11",
            "d15_12",
            "d15_13",
            "d15_14"
        )
        private val d17Image = arrayListOf(
            "d17_01",
            "d17_02",
            "d17_03",
            "d17_04",
            "d17_05",
            "d17_06",
            "d17_07",
            "d17_08",
            "d17_09",
            "d17_10",
            "d17_11",
            "d17_12",
            "d17_13",
            "d17_14",
            "d17_15",
            "d17_16",
            "d17_17",
            "d17_18",
            "d17_19",
            "d17_20",
            "d17_21",
            "d17_22",
            "d17_23",
            "d17_24"
        )
        private val d18Image = arrayListOf(
            "d18_01",
            "d18_02",
            "d18_03",
            "d18_04",
            "d18_05",
            "d18_06",
            "d18_07",
            "d18_08",
            "d18_09",
            "d18_10",
            "d18_11",
            "d18_12",
            "d18_13",
            "d18_14",
            "d18_15",
            "d18_16",
            "d18_17"
        )
        private val d19Image = arrayListOf(
            "d19_01",
            "d19_02",
            "d19_03",
            "d19_04",
            "d19_05",
            "d19_06",
            "d19_07",
            "d19_08",
            "d19_09",
            "d19_10",
            "d19_11",
            "d19_12",
            "d19_13",
            "d19_14",
            "d19_15",
            "d19_16",
            "d19_17",
            "d19_18",
            "d19_19",
            "d19_20",
            "d19_21",
            "d19_22",
            "d19_23",
            "d19_24",
            "d19_25",
            "d19_26"
        )
        private val d20Image = arrayListOf(
            "d20_01",
            "d20_02",
            "d20_03",
            "d20_04",
            "d20_05",
            "d20_06",
            "d20_07",
            "d20_08",
            "d20_09",
            "d20_10",
            "d20_11",
            "d20_12",
            "d20_13",
            "d20_14",
            "d20_15",
            "d20_16",
            "d20_17",
            "d20_18",
            "d20_19",
            "d20_20"
        )
        private val d21Image = arrayListOf(
            "d21_01",
            "d21_02",
            "d21_03",
            "d21_04",
            "d21_05",
            "d21_06",
            "d21_07",
            "d21_08",
            "d21_09",
            "d21_10",
        )
        private val d22Image = arrayListOf(
            "d22_01",
            "d22_02",
            "d2203",
            "d22_04",
            "d22_05",
            "d22_06",
            "d22_07",
            "d22_08",
            "d22_09",
            "d22_10",
            "d22_11",
            "d22_12"
        )
        private val d23Image = arrayListOf(
            "d23_01",
            "d23_02",
            "d23_03",
            "d23_04",
            "d23_05",
            "d23_06",
            "d23_07",
            "d23_08",
            "d23_09",
            "d23_10"
        )
        private val d24Image = arrayListOf(
            "d24_01",
            "d24_02",
            "d24_03",
            "d24_04",
            "d24_05",
            "d24_06",
            "d24_07",
            "d24_08",
            "d24_09",
            "d24_10"
        )
        private val d25Image = arrayListOf(
            "d25_01",
            "d25_02",
            "d25_03",
            "d25_04",
            "d25_05",
            "d25_06",
            "d25_07",
            "d25_08",
            "d25_09",
            "d25_10",
            "d25_11",
            "d25_12",
            "d25_13"
        )
        private val d26Image = arrayListOf(
            "d26_01",
            "d26_02",
            "d26_03",
            "d26_04",
            "d26_05",
            "d26_06",
            "d26_07",
            "d26_08",
            "d26_09",
            "d26_10",
            "d26_11",
            "d26_12"
        )
        private val d27Image = arrayListOf(
            "d27_01",
            "d27_02",
            "d27_03",
            "d27_04",
            "d27_05",
            "d27_06",
            "d27_07",
            "d27_08",
            "d27_09",
            "d27_10",
            "d27_11",
            "d27_12",
            "d27_13",
            "d27_14",
            "d27_15"
        )
        private val d28Image = arrayListOf(
            "d28_01",
            "d28_02",
            "d28_03",
            "d28_04",
            "d28_05",
            "d28_06",
            "d28_07",
            "d28_08",
            "d28_09"
        )
    }
}