/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.navigation.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.get
import kotlin.reflect.KClass

/**
 * Construct a new [DialogFragmentNavigator.Destination]
 *
 * @param id the destination's unique id
 */
@Suppress("Deprecation")
@Deprecated(
    "Use routes to create your DialogFragmentDestination instead",
    ReplaceWith("dialog<F>(route = id.toString())")
)
inline fun <reified F : DialogFragment> NavGraphBuilder.dialog(
    @IdRes id: Int
): Unit = dialog<F>(id) {}

/**
 * Construct a new [DialogFragmentNavigator.Destination]
 *
 * @param id the destination's unique id
 * @param builder the builder used to construct the fragment destination
 */
@Suppress("Deprecation")
@Deprecated(
    "Use routes to create your DialogFragmentDestination instead",
    ReplaceWith("dialog<F>(route = id.toString()) { builder.invoke() }")
)
inline fun <reified F : DialogFragment> NavGraphBuilder.dialog(
    @IdRes id: Int,
    builder: DialogFragmentNavigatorDestinationBuilder.() -> Unit
): Unit = destination(
    DialogFragmentNavigatorDestinationBuilder(
        provider[DialogFragmentNavigator::class],
        id,
        F::class
    ).apply(builder)
)

/**
 * Construct a new [DialogFragmentNavigator.Destination]
 *
 * @param route the destination's unique route
 */
inline fun <reified F : DialogFragment> NavGraphBuilder.dialog(
    route: String
): Unit = dialog<F>(route) {}

/**
 * Construct a new [DialogFragmentNavigator.Destination]
 *
 * @param route the destination's unique route
 * @param builder the builder used to construct the fragment destination
 */
inline fun <reified F : DialogFragment> NavGraphBuilder.dialog(
    route: String,
    builder: DialogFragmentNavigatorDestinationBuilder.() -> Unit
): Unit = destination(
    DialogFragmentNavigatorDestinationBuilder(
        provider[DialogFragmentNavigator::class],
        route,
        F::class
    ).apply(builder)
)

/**
 * DSL for constructing a new [DialogFragmentNavigator.Destination]
 */
@NavDestinationDsl
class DialogFragmentNavigatorDestinationBuilder :
    NavDestinationBuilder<DialogFragmentNavigator.Destination> {

    private var fragmentClass: KClass<out DialogFragment>

    /**
     * DSL for constructing a new [DialogFragmentNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param id the destination's unique id
     * @param fragmentClass the class name of the DialogFragment to show when you navigate to this
     * destination
     */
    @Suppress("Deprecation")
    @Deprecated(
        "Use routes to build your DialogFragmentNavigatorDestination instead",
        ReplaceWith(
            "DialogFragmentNavigatorDestinationBuilder(navigator, route = id.toString(), " +
                    "fragmentClass) "
        )
    )
    constructor(
        navigator: DialogFragmentNavigator,
        @IdRes id: Int,
        fragmentClass: KClass<out DialogFragment>
    ) : super(navigator, id) {
        this.fragmentClass = fragmentClass
    }

    /**
     * DSL for constructing a new [DialogFragmentNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param route the destination's unique route. This sets the [route] on the newly
     * constructed [NavDestination]. This can be any valid non-empty String.
     * @param fragmentClass the class name of the DialogFragment to show when you navigate to this
     * destination
     */
    constructor(
        navigator: DialogFragmentNavigator,
        route: String,
        fragmentClass: KClass<out DialogFragment>
    ) : super(navigator, route) {
        this.fragmentClass = fragmentClass
    }

    override fun build(): DialogFragmentNavigator.Destination =
        super.build().also { destination ->
            destination.setClassName(fragmentClass.java.name)
        }
}
