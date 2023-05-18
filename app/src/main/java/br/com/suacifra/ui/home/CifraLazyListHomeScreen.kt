package br.com.suacifra.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.com.suacifra.R
import br.com.suacifra.data.models.Cifra
import br.com.suacifra.ui.theme.Dimension
import br.com.suacifra.ui.widgets.CifraCard

@Composable
fun CifraLazyListHomeScreen(
    cifraList: List<Cifra>
) {
    Box(
        modifier = Modifier
            .padding(Dimension.smallPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimension.mediumPadding)
        ) {
            Text(
                text = stringResource(id = R.string.main_app_name),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimension.mediumPadding),
                verticalArrangement = Arrangement.spacedBy(Dimension.smallPadding)
            ) {
                items(cifraList.size) {
                    CifraCard(modifier = Modifier.fillMaxSize(), cifra = cifraList[it])
                }
            }
        }
    }
}
