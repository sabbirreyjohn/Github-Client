package xyz.androidrey.githubclient.theme.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import xyz.androidrey.githubclient.theme.AppTheme

@Composable
fun TheTextField(
    value: String,
    label: String,
    hint: String = "",
    onValueChanged: (String) -> Unit,
    isPasswordField: Boolean = false,
    isClickOnly: Boolean = false,
    onClick: () -> Unit = {},
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent
    ),
    shape: Shape = RoundedCornerShape(12.dp),
    onDone: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier
                .clickable {
                    if (isClickOnly) {
                        onClick()
                    }
                },
            value = value,
            onValueChange = { onValueChanged(it) },
            singleLine = singleLine,
            isError = error != null,
            readOnly = isClickOnly,
            enabled = !isClickOnly,
            visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
            label = { Text(text = label) },
            placeholder = { Text(text = hint) },
            leadingIcon = {
                leadingIcon?.let {
                    Icon(it, hint, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            trailingIcon = {
                if (error != null) {
                    Icon(Icons.Filled.Info, "error", tint = MaterialTheme.colorScheme.error)
                }
            },
            shape = shape,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onDone()
                },
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            colors = colors
        )
    }

@ThePreview
@Composable
fun AppTextFieldPreview() {
    AppTheme {
        Surface {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {},
                value = "",
                onValueChange = { },
                singleLine = true,
                isError = true, // @Update Error State
                readOnly = false,
                enabled = true,
                supportingText = {
                    Text(text = "Error Message or Supporting Message")
                },
                placeholder = { Text(text = "Hint") },
            )
        }
    }
}
