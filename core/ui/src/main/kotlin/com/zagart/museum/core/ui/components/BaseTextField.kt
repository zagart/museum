package com.zagart.museum.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zagart.museum.core.ui.icons.IconsProvider

typealias onActionResult = () -> Unit

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    nameValue: String = "",
    hintValue: String = "",
    contentValue: String = "",
    onNewValue: (String) -> Unit = {},
    editable: Boolean = true,
    errorState: Boolean = false,
    action: BaseTextFieldAction? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    converter: BaseTextFieldConverter = BaseTextFieldConverter()
) {
    val focusRequester = remember { FocusRequester() }
    var contentHasError by rememberSaveable { mutableStateOf(errorState) }
    var isActionInProgress by rememberSaveable { mutableStateOf(false) }
    var hasFocus by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.animateContentSize()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    hasFocus = it.hasFocus

                    if (!it.hasFocus) {
                        isActionInProgress = false
                        contentHasError = false
                    }
                },
            label = {
                Text(text = nameValue)
            },
            supportingText = if (!isActionInProgress && hintValue.isNotEmpty()) {
                { Text(text = hintValue) }
            } else {
                null
            },
            trailingIcon = {
                if (action != null) {
                    IconButton(
                        modifier = Modifier.alpha(if (contentHasError) 0.3f else 1f),
                        enabled = !contentHasError,
                        onClick = {
                            if (isActionInProgress) {
                                action.onProgressIconPressed()
                            } else {
                                action.onNormalIconPressed()
                            }

                            focusRequester.requestFocus()
                            isActionInProgress = !isActionInProgress
                        }) {
                        Icon(
                            painter = rememberVectorPainter(image = if (isActionInProgress) action.progressIcon else action.normalIcon),
                            contentDescription = nameValue,
                            tint = if (hasFocus) MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }
                }
            },
            value = contentValue,
            isError = contentHasError,
            readOnly = !editable,
            onValueChange = { newName ->
                if (converter.valueValidator(newName)) {
                    onNewValue(converter.valueFilter(newName))
                }

                contentHasError = converter.errorDetector(newName)
            },
            visualTransformation = visualTransformation,
            textStyle = MaterialTheme.typography.titleMedium
        )

        if (isActionInProgress) {
            Box {
                val borderModifier = Modifier
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        shape = CustomRectangleShape(-5f)
                    )
                    .animateContentSize()
                action?.actionContent?.invoke(borderModifier) {
                    isActionInProgress = false
                }
            }
        }
    }
}

data class BaseTextFieldAction(
    val normalIcon: ImageVector = IconsProvider.getMoreIcon(true),
    val progressIcon: ImageVector = IconsProvider.getMoreIcon(false),
    val enabled: Boolean = true,
    val onNormalIconPressed: () -> Unit = {},
    val onProgressIconPressed: () -> Unit = {},
    val actionContent: @Composable ((Modifier, onActionResult) -> Unit) = { _, _ -> }
)

data class CustomRectangleShape(val customTop: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            moveTo(0f, size.height)
            lineTo(0f, customTop)
            lineTo(size.width, customTop)
            lineTo(size.width, size.height)
            close()
        })
    }
}

open class BaseTextFieldConverter {

    /**
     * Converts value based on user input.
     */
    open val valueFilter: (String) -> String = { it }

    /**
     * Doesn't affect value itself, but changes its' representation.
     */
    open val valueTransformer: (String) -> String = { it }

    /**
     * Defines whether value can be shown (triggered before filter)
     */
    open val valueValidator: (String) -> Boolean = { true }

    /**
     * Defines whether entered value is error, yet do not restrict it.
     */
    open val errorDetector: (String) -> Boolean = { false }

    /**
     * Doesn't affect suffix itself, but changes its' representation.
     */
    open val suffixTransformer: (String) -> String = { it }
}