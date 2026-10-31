package com.aditya.monepurse.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditya.monepurse.R
import com.aditya.monepurse.core.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    // UI State for inputs
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(true) }

    // New Profile Fields
    var fullName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var income by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val currencyOptions = listOf(
        Pair("United States ($ • USD)", "$"),
        Pair("United Kingdom (£ • GBP)", "£"),
        Pair("India (₹ • INR)", "₹"),
        Pair("Eurozone (€ • EUR)", "€")
    )
    var currencyDropdownExpanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf(currencyOptions[0]) }

    var selectedSalaryDay by remember { mutableIntStateOf(1) }
    var showSalaryDayDialog by remember { mutableStateOf(false) }

    // Reusable light border color for inputs
    val borderColor = Color(0xFFE2E8F0)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {}, //{ Text("Create account", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = BrandDark) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrandDark)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundWhite)
                // windowInsets removed because Scaffold's systemBarsPadding handles the top edge automatically now
            )
        },
        containerColor = BackgroundWhite,
        modifier = Modifier.imePadding().systemBarsPadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            //Spacer(modifier = Modifier.height(16.dp))

            // Header Section
            Text(
                text = "Create your account",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BrandDark
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Email Field
            Text("Email Address", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("alex.morgan@example.com", color = TextGray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "Email", tint = TextGray, modifier = Modifier.size(20.dp)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = BrandBlue
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Password Field Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Password", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
                Text("Must be 8+ chars", fontSize = 10.sp, color = BrandBlue, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("••••••••••••", color = TextGray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = "Password", tint = TextGray, modifier = Modifier.size(20.dp)) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password Visibility",
                            tint = TextGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = BrandBlue
                )
            )

            // Password Strength Indicator (Only shows if password is not empty)
            if (password.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 1f }, // You can add logic later to calculate actual strength 0.0f to 1.0f
                    modifier = Modifier.fillMaxWidth().height(3.dp),
                    color = BrandBlue,
                    trackColor = borderColor,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BrandBlue, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Strong password • Ready to save securely", fontSize = 10.sp, color = BrandBlue)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Full Name
            Text("Full Name", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("Alex Morgan", color = TextGray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = "Full Name", tint = TextGray, modifier = Modifier.size(20.dp)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = BrandBlue
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Date of Birth

            val datePickerState = rememberDatePickerState()
            
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                            datePickerState.selectedDateMillis?.let { millis ->
                                val sdf = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                                dob = sdf.format(java.util.Date(millis))
                            }
                        }) {
                            Text("OK", color = BrandBlue)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel", color = TextGray)
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Text("Date of Birth", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = dob,
                onValueChange = { dob = it },
                placeholder = { Text("14 May 1994", color = TextGray, fontSize = 14.sp) },
                leadingIcon = { 
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Outlined.DateRange, contentDescription = "Date of Birth", tint = TextGray, modifier = Modifier.size(20.dp)) 
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = BrandBlue
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Base Currency & Country
            Text("Base Currency & Country", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = currencyDropdownExpanded,
                onExpandedChange = { currencyDropdownExpanded = !currencyDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCurrency.first,
                    onValueChange = { },
                    readOnly = true,
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = currencyDropdownExpanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = borderColor,
                        focusedBorderColor = BrandBlue
                    )
                )
                ExposedDropdownMenu(
                    expanded = currencyDropdownExpanded,
                    onDismissRequest = { currencyDropdownExpanded = false }
                ) {
                    currencyOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.first, fontSize = 14.sp) },
                            onClick = {
                                selectedCurrency = option
                                currencyDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            //Text("Dynamically sets currency symbol (${selectedCurrency.second}) across all screens", fontSize = 10.sp, color = TextGray)
            //Spacer(modifier = Modifier.height(16.dp))

            Text("Monthly Take-Home Income", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = income,
                onValueChange = { income = it },
                placeholder = { Text("6,500.00", color = TextGray, fontSize = 14.sp) },
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
                leadingIcon = {
                    Text(
                        text = selectedCurrency.second,
                        fontWeight = FontWeight.Bold,
                        color = BrandDark,
                        fontSize = 14.sp,
                        // Reduced from 16.dp to 8.dp (or remove the modifier completely for minimum padding)
                        modifier = Modifier.padding(end = 1.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = BrandBlue
                ),
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("Salary Deposit Schedule", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BrandDark)
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                    .clickable { showSalaryDayDialog = true }
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val suffix = when {
                            selectedSalaryDay in 11..13 -> "th"
                            selectedSalaryDay % 10 == 1 -> "st"
                            selectedSalaryDay % 10 == 2 -> "nd"
                            selectedSalaryDay % 10 == 3 -> "rd"
                            else -> "th"
                        }
                        Text("${selectedSalaryDay}$suffix of every month", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = BrandDark)
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextGray, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Automatic reset on deposit day", fontSize = 10.sp, color = TextGray)
                        Text("Auto-update", fontSize = 10.sp, color = BrandBlue, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Terms & Conditions Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).offset(x = (-8).dp)
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = { termsAccepted = it },
                    colors = CheckboxDefaults.colors(checkedColor = BrandBlue),
                    modifier = Modifier.scale(0.8f) // Scale down the checkbox itself
                )
                // buildAnnotatedString allows us to mix gray and blue text in one paragraph
                val termsText = buildAnnotatedString {
                    append("I agree to ")
                    withStyle(style = SpanStyle(color = BrandBlue, fontWeight = FontWeight.Medium)) { append("Terms of Service") }
                    append(" and ")
                    withStyle(style = SpanStyle(color = BrandBlue, fontWeight = FontWeight.Medium)) { append("Privacy Policy") }
                    append(".")
                }
                Text(
                    text = termsText, 
                    fontSize = 11.sp, 
                    color = TextGray, 
                    lineHeight = 16.sp,
                    modifier = Modifier.offset(x = (-4).dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primary Continue Button
            Button(
                onClick = onContinueClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Complete Setup", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Divider Section
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = borderColor)
                Text("or continue with", modifier = Modifier.padding(horizontal = 12.dp), fontSize = 11.sp, color = TextGray)
                HorizontalDivider(modifier = Modifier.weight(1f), color = borderColor)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Google Button
            OutlinedButton(
                onClick = { /* Handle Google Auth later */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandDark),
                border = BorderStroke(1.dp, borderColor)
            ) {
                // Temporary text logo for Google until you add the actual PNG/SVG icon
                //Text("G", fontWeight = FontWeight.ExtraBold, color = Color(0xFFDB4437), fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Continue with Google", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(10.dp))


        }
    }
}