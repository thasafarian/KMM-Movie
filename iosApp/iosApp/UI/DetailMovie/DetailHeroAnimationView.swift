//
//  DetailHeroAnimationView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 01/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import YouTubePlayerKit
import shared

struct DetailHeroAnimationView : View {
    
    let namespace: Namespace.ID
    var imageUrl: String
    
    var body: some View {
        let youTubePlayer: YouTubePlayer = "https://www.youtube.com/watch?v=uYPbbksJxIg"
        
        GeometryReader { proxy in
            ScrollView {
                VStack(spacing: 20) {
                    //MARK: YOUTUBE PLAYER
                    YouTubePlayerView(youTubePlayer) { state in
                        switch state {
                        case .idle:
                            ProgressView()
                        case .ready:
                            EmptyView()
                        case .error(_):
                            Text(verbatim: "YouTube player couldn't be loaded")
                        }
                    }
                    .frame(width: proxy.size.width, height: 250)
                }.onAppear() {
                    youTubePlayer.configuration.autoPlay = true
                }
                .matchedGeometryEffect(id: "playmovieeffect", in: namespace)
            }
        }
    }
    
}
