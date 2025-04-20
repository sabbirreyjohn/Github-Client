package xyz.androidrey.githubclient.main.ui.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.common.data.util.createDummyUser
import xyz.androidrey.githubclient.theme.components.ThePreview
import xyz.androidrey.githubclient.theme.components.TheTextField

@Composable
fun UserList(
    users: List<User>,
    query: String,
    viewModel: UsersViewModel,
    onSelect: (String) -> Unit
) {
    Column {
        // 🛠️ Static top search bar
        TheTextField(
            value = query,
            onValueChanged = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            hint = "Type Username",
            leadingIcon = Icons.Default.Search,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            label = "Search Users"
        )

        // ✅ LazyColumn handles only scrolling content
        LazyColumn(
            modifier = Modifier.padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users.size) { userIndex ->
                val user = users[userIndex]
                UserCard(user = user) {
                    onSelect(it)
                }
            }

            if (users.isEmpty()) {
                item {
                    Text(
                        text = "No users found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun UserCard(
    user: User,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onClick(user.login) },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = user.avatar_url,
                contentDescription = "${user.login} avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = user.login,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Tap to view repos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@ThePreview
@Composable
fun TeacherCardPreview() {
    UserCard(user=createDummyUser()){}
}
