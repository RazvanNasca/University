package app.exam.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.material.datepicker.MaterialDatePicker

@Composable
fun DatePickerview(
    datePicked : String,
    updatedDate : ( date : Long ) -> Unit,
    changed : MutableState<Boolean>,
    isError: MutableState<Boolean>,
    isErrorFunc : () -> Unit
) {
    val activity = LocalContext.current as AppCompatActivity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable{
                showDatePicker(activity, updatedDate, changed, isError, isErrorFunc)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (label, errorView, iconView) = createRefs()

            Text(
                text = if (datePicked.isEmpty()) "Pick a date" else datePicked,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        if(isError.value){
                            end.linkTo(errorView.start)
                        } else{
                            end.linkTo(iconView.start)
                        }
                        width = Dimension.fillToConstraints
                    }
            )

            if(isError.value){
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "error",
                    tint = MaterialTheme.colors.error,
                    modifier = Modifier
                        .size(20.dp, 20.dp)
                        .constrainAs(errorView){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(label.end)
                            end.linkTo(iconView.start)
                        }
                )
            }

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        if(isError.value){
                            start.linkTo(errorView.end)
                        }
                    },
                tint = MaterialTheme.colors.onSurface
            )

        }
    }
}

private fun showDatePicker(
    activity : AppCompatActivity,
    updatedDate: (Long) -> Unit,
    changed : MutableState<Boolean>,
    isError: MutableState<Boolean>,
    isErrorFunc : () -> Unit
    )
{
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updatedDate(it)
        changed.value = true
        isError.value = false
        isErrorFunc()
    }
}