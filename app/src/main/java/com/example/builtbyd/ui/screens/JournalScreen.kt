package com.example.builtbyd.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.builtbyd.model.AppContentRepository
import com.example.builtbyd.model.ArticleCategory
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltBurgundy
import com.example.builtbyd.ui.theme.BuiltCardBg
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltIvorySoft
import com.example.builtbyd.ui.theme.BuiltLine

@Composable
fun JournalScreen(
    selectedCategory: ArticleCategory,
    onCategorySelected: (ArticleCategory) -> Unit,
    selectedArticleId: String?,
    onArticleSelected: (String?) -> Unit,
    onOpenConsultation: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val filteredArticles = if (selectedCategory == ArticleCategory.ALL) {
        AppContentRepository.articles
    } else {
        AppContentRepository.articles.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BuiltBlack)
            .padding(horizontal = 18.dp)
            .testTag("journal_screen_lazy_column")
    ) {
        // Return button
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable { onBackToHome() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Return to Front Room",
                    tint = BuiltGoldLight
                )
                Text(
                    text = " Return to Built by D",
                    color = BuiltGoldLight,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Hero Header
        item {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = "TRAINING · NUTRITION · WOMEN · MINDSET",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "The Built by D Journal",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Text(
                    text = "Strong answers for women building capable bodies at every age.",
                    color = BuiltIvorySoft,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                // Journal Hero Banner Graphic
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, BuiltGold.copy(alpha = 0.5f))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/assets/media-11-9a443f1b8f.jpg")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Hormone optimization and strength banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                items(ArticleCategory.values()) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategorySelected(category) },
                        label = {
                            Text(
                                text = category.displayName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BuiltGold,
                            selectedLabelColor = BuiltBlack,
                            containerColor = BuiltCardBg,
                            labelColor = BuiltIvorySoft
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) BuiltGold else BuiltLine,
                            enabled = true,
                            selected = isSelected
                        )
                    )
                }
            }
        }

        // Articles List
        items(filteredArticles) { article ->
            val isExpanded = selectedArticleId == article.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        onArticleSelected(if (isExpanded) null else article.id)
                    },
                colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                border = BorderStroke(1.dp, if (isExpanded) BuiltGold else BuiltLine),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${article.category.displayName.uppercase()} · ${article.readTime}",
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = BuiltGoldLight
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${article.number}. ${article.title}",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Takeaway Callout Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BuiltBlack.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                            .border(BorderStroke(1.dp, BuiltGold.copy(alpha = 0.4f)), RoundedCornerShape(4.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "KEY TAKEAWAY",
                                color = BuiltGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = article.takeaway,
                                color = BuiltIvory,
                                fontStyle = FontStyle.Italic,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    // Expanded Answers
                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 14.dp)) {
                            article.answers.forEach { paragraph ->
                                Text(
                                    text = paragraph,
                                    color = BuiltIvorySoft,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // CTA Banner at bottom
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                colors = CardDefaults.cardColors(containerColor = BuiltBurgundy),
                border = BorderStroke(1.dp, BuiltGold),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Build your plan with Deana.",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Reading gives direction. Training creates change.",
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                    Button(
                        onClick = onOpenConsultation,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BuiltGold,
                            contentColor = BuiltBlack
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("Start Consultation Intake", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
