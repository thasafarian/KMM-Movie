//
//  MainScreen.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 21/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import SwiftUIPager
import YouTubePlayerKit

struct HomeView: View {
    @StateObject var viewModel = MainViewModel()
    
    var body: some View {
        NavigationView {
            GeometryReader { proxy in
                ScrollView(showsIndicators: false) {
                    VStack(alignment: .leading) {
                            // Now Playing Movies
                            Title(
                                title: "Now Playing",
                                subtitle: "All movies playing in your local cinemas"
                            )
                            NowPlayingView(proxy: proxy)
                            
                            // Popular Movies
                            Title(
                                title: "Popular"
                            ).padding(.top, 20)
                            PopularView(proxy: proxy)

                            // Top Rated Movies
                            Title(
                                title: "Top Rated"
                            ).padding(.top, 20)
                            TopRatedView(proxy: proxy)
                    }
                }
            }
        }    .navigationViewStyle(StackNavigationViewStyle())

    }
}
