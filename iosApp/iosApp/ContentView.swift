import SwiftUI
import Combine
import shared

struct ContentView: View {
    var body: some View {
        TabView() {
            HomeView()
                .tabItem {
                    Image(systemName: "house.fill")
                    Text("Home")
                }

            SearchView()
                .tabItem {
                    Image(systemName: "magnifyingglass")
                    Text("Explore")
                }

            VideoView()
                .tabItem {
                    Image(systemName: "play.circle.fill")
                    Text("Playlist")
                }
        }
    }
}
