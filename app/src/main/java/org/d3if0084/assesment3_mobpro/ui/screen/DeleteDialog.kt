package org.d3if0084.assesment3_mobpro.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if0084.assesment3_mobpro.R
import org.d3if0084.assesment3_mobpro.ui.theme.Assesment3_MobproTheme

@Composable
fun DeleteDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            text = { Text(text = stringResource(id = R.string.pesan_hapus)) },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmation() }
                ) {
                    Text(text = stringResource(id = R.string.hapus))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = stringResource(id = R.string.batal))
                }
            },
            onDismissRequest = { onDismissRequest() }
        )
    }

}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DeletePreview() {
    Assesment3_MobproTheme {
        DeleteDialog(
            openDialog = true,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}