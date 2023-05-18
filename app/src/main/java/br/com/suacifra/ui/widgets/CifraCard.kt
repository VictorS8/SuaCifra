package br.com.suacifra.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import br.com.suacifra.R
import br.com.suacifra.data.models.Cifra
import br.com.suacifra.ui.theme.Dimension

@Composable
fun CifraCard(modifier: Modifier, cifra: Cifra) {
    Card(
        modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimension.mediumPadding),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = BorderStroke(
            width = Dimension.cardBorderStrokeWidth, brush = Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimension.mediumPadding),
            verticalArrangement = Arrangement.spacedBy(Dimension.smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cifra.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                cifra.singerName?.let { singerName ->
                    Text(
                        text = stringResource(id = R.string.cifra_singer_name_item, singerName),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = Dimension.smallPadding)
                            .fillMaxWidth(0.5F),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                cifra.tone?.let { it1 ->
                    Text(
                        text = stringResource(id = R.string.cifra_tone_item, it1),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = Dimension.smallPadding)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
