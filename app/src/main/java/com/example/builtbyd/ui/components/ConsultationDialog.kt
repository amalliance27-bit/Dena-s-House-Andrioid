package com.example.builtbyd.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.builtbyd.model.AppContentRepository
import com.example.builtbyd.model.ConsultationForm
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltBurgundy
import com.example.builtbyd.ui.theme.BuiltBurgundyDeep
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltIvorySoft
import com.example.builtbyd.ui.theme.BuiltLine

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConsultationDialog(
    isOpen: Boolean,
    currentStep: Int,
    formState: ConsultationForm,
    step1Error: String?,
    onClose: () -> Unit,
    onToggleGoal: (String) -> Unit,
    onUpdateService: (String) -> Unit,
    onUpdateExperience: (String) -> Unit,
    onUpdateAgeRange: (String) -> Unit,
    onUpdateDays: (String) -> Unit,
    onUpdateSuccess: (String) -> Unit,
    onUpdateFirstName: (String) -> Unit,
    onUpdateLastName: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateSocial: (String) -> Unit,
    onUpdateNotes: (String) -> Unit,
    onUpdateConsent: (Boolean) -> Unit,
    onNextStep: () -> Boolean,
    onPrevStep: () -> Unit,
    onSendEmail: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isOpen) return

    val context = LocalContext.current

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .border(1.dp, BuiltGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .testTag("consultation_dialog_surface"),
            color = BuiltBlack,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .testTag("close_consultation_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close dialog",
                        tint = BuiltGoldLight
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    // Header with Progress Indicator
                    Text(
                        text = "PRECISION PHYSIQUE",
                        color = BuiltGoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = if (currentStep == 4) "Intake Complete" else "Let’s build your starting point.",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val progressFraction = when (currentStep) {
                        1 -> 0.33f
                        2 -> 0.66f
                        3 -> 0.95f
                        else -> 1f
                    }
                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = BuiltGold,
                        trackColor = Color(0x33D0A65A)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (currentStep == 4) "Brief Ready" else "Step $currentStep of 3",
                            color = BuiltGoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = when (currentStep) {
                                1 -> "Goals"
                                2 -> "Coaching & Background"
                                3 -> "Contact Info"
                                else -> "Review & Send"
                            },
                            color = BuiltIvorySoft.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }

                    // Step Content (Scrollable)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        when (currentStep) {
                            1 -> {
                                Text(
                                    text = "What would you most like to achieve?",
                                    color = BuiltIvory,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                AppContentRepository.goalOptions.forEach { (goalTitle, goalSub) ->
                                    val isChecked = formState.selectedGoals.contains(goalTitle)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .background(
                                                if (isChecked) BuiltBurgundyDeep else Color(0x1AFFFFFF),
                                                RoundedCornerShape(6.dp)
                                            )
                                            .border(
                                                1.dp,
                                                if (isChecked) BuiltGold else Color(0x2BD0A65A),
                                                RoundedCornerShape(6.dp)
                                            )
                                            .clickable { onToggleGoal(goalTitle) }
                                            .padding(12.dp)
                                            .testTag("goal_choice_$goalTitle")
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .background(
                                                        if (isChecked) BuiltGold else Color.Transparent,
                                                        RoundedCornerShape(4.dp)
                                                    )
                                                    .border(
                                                        1.dp,
                                                        BuiltGold,
                                                        RoundedCornerShape(4.dp)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                if (isChecked) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = null,
                                                        tint = BuiltBlack,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = goalTitle,
                                                    color = BuiltIvory,
                                                    fontFamily = FontFamily.Serif,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp
                                                )
                                                Text(
                                                    text = goalSub,
                                                    color = BuiltIvorySoft,
                                                    fontSize = 12.sp,
                                                    lineHeight = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }

                                if (step1Error != null) {
                                    Text(
                                        text = step1Error,
                                        color = Color(0xFFFF6B81),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Hormone-related coaching supports training and lifestyle habits. Medical diagnosis and treatment stay with your licensed clinician.",
                                    color = BuiltIvorySoft.copy(alpha = 0.6f),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }

                            2 -> {
                                Text(
                                    text = "Your Coaching Preferences",
                                    color = BuiltIvory,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 14.dp)
                                )

                                DropdownSelector(
                                    label = "Preferred coaching",
                                    options = AppContentRepository.serviceOptions,
                                    selectedOption = formState.service,
                                    onOptionSelected = onUpdateService
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                DropdownSelector(
                                    label = "Training experience",
                                    options = AppContentRepository.experienceOptions,
                                    selectedOption = formState.experience,
                                    onOptionSelected = onUpdateExperience
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                DropdownSelector(
                                    label = "Age range",
                                    options = AppContentRepository.ageRangeOptions,
                                    selectedOption = formState.ageRange,
                                    onOptionSelected = onUpdateAgeRange
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                DropdownSelector(
                                    label = "Days available each week",
                                    options = AppContentRepository.daysOptions,
                                    selectedOption = formState.daysAvailable,
                                    onOptionSelected = onUpdateDays
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = formState.success90Days,
                                    onValueChange = onUpdateSuccess,
                                    label = { Text("What would success look and feel like in 90 days?") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_success_90_days"),
                                    minLines = 3,
                                    colors = consultationTextFieldColors()
                                )
                            }

                            3 -> {
                                Text(
                                    text = "Contact Details",
                                    color = BuiltIvory,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 14.dp)
                                )

                                OutlinedTextField(
                                    value = formState.firstName,
                                    onValueChange = onUpdateFirstName,
                                    label = { Text("First Name *") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_first_name"),
                                    singleLine = true,
                                    colors = consultationTextFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = formState.lastName,
                                    onValueChange = onUpdateLastName,
                                    label = { Text("Last Name *") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_last_name"),
                                    singleLine = true,
                                    colors = consultationTextFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = formState.email,
                                    onValueChange = onUpdateEmail,
                                    label = { Text("Email Address *") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_email"),
                                    singleLine = true,
                                    colors = consultationTextFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = formState.socialHandle,
                                    onValueChange = onUpdateSocial,
                                    label = { Text("Instagram / Social handle (Optional)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_social_handle"),
                                    singleLine = true,
                                    colors = consultationTextFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = formState.notes,
                                    onValueChange = onUpdateNotes,
                                    label = { Text("Anything Deana should know before speaking?") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_notes"),
                                    minLines = 2,
                                    colors = consultationTextFieldColors()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable { onUpdateConsent(!formState.consent) }
                                        .padding(vertical = 4.dp)
                                ) {
                                    Checkbox(
                                        checked = formState.consent,
                                        onCheckedChange = onUpdateConsent,
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = BuiltGold,
                                            uncheckedColor = BuiltLine,
                                            checkmarkColor = BuiltBlack
                                        )
                                    )
                                    Text(
                                        text = "I agree to be contacted regarding this consultation request.",
                                        color = BuiltIvorySoft,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            4 -> {
                                // Success Screen
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp)
                                            .border(1.dp, BuiltGold),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "D",
                                            color = BuiltGoldLight,
                                            fontFamily = FontFamily.Serif,
                                            fontStyle = FontStyle.Italic,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 32.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Your consultation brief is prepared.",
                                        color = BuiltIvory,
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "Deana replies from deanambrady@hotmail.com, usually within one business day.",
                                        color = BuiltIvorySoft,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )

                                    val brief = formState.generateBrief()
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF1E1719), RoundedCornerShape(6.dp))
                                            .border(1.dp, BuiltLine, RoundedCornerShape(6.dp))
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            text = brief,
                                            color = BuiltGoldLight,
                                            fontSize = 11.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Actions Footer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (currentStep in 2..3) {
                            TextButton(
                                onClick = onPrevStep,
                                modifier = Modifier.testTag("intake_back_button")
                            ) {
                                Text("Back", color = BuiltIvorySoft, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        if (currentStep < 3) {
                            Button(
                                onClick = { onNextStep() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BuiltGold,
                                    contentColor = BuiltBlack
                                ),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.testTag("intake_next_button")
                            ) {
                                Text("Continue", fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                            }
                        } else if (currentStep == 3) {
                            Button(
                                onClick = { onNextStep() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BuiltGold,
                                    contentColor = BuiltBlack
                                ),
                                shape = RoundedCornerShape(4.dp),
                                enabled = formState.isStep3Valid,
                                modifier = Modifier.testTag("intake_submit_button")
                            ) {
                                Text("Prepare Consultation", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            // Step 4 Actions: Copy & Email
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Built by D Consultation", formState.generateBrief())
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Brief copied to clipboard", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0x33D0A65A),
                                        contentColor = BuiltGoldLight
                                    ),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.testTag("copy_brief_button")
                                ) {
                                    Text("Copy Brief")
                                }

                                Button(
                                    onClick = onSendEmail,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = BuiltGold,
                                        contentColor = BuiltBlack
                                    ),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.testTag("email_brief_button")
                                ) {
                                    Text("Email Deana", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOption.ifBlank { "Choose one" },
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = consultationTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(BuiltBlack).border(1.dp, BuiltLine)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (option == selectedOption) BuiltGold else BuiltIvory,
                            fontSize = 13.sp
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun consultationTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = BuiltGold,
    unfocusedBorderColor = BuiltLine,
    focusedLabelColor = BuiltGoldLight,
    unfocusedLabelColor = BuiltIvorySoft.copy(alpha = 0.7f),
    focusedTextColor = BuiltIvory,
    unfocusedTextColor = BuiltIvory,
    cursorColor = BuiltGold
)
