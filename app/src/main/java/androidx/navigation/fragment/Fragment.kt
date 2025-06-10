package androidx.navigation.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

fun Fragment.findNavController(): NavController = NavHostFragment.findNavController(this)