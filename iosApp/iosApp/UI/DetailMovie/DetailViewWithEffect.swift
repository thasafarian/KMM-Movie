//
//  DetailViewWithEffect.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 26/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import YouTubePlayerKit
import shared

struct DetailViewWithEffect: View {
    
    var body: some View {
        VStack(alignment: .leading,spacing: 20) {
            
            //            //MARK: TITLE AND BACK ACTION
            //            HStack {
            //                Image(systemName: "chevron.left")
            //                    .onTapGesture {
            //                        selectedMovie = nil
            //                    }
            //                Title(title: selectedMovie!.movieTitle)
            //                    .onTapGesture {
            //                        selectedMovie = nil
            //                    }
            //            }
            
            //MARK: CONTENT
            let youTubePlayer: YouTubePlayer = "https://www.youtube.com/watch?v=uYPbbksJxIg"
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
                    .frame(width: .infinity, height: 250)
                }
                
                //MARK: MOVIE DESCRIPTION
                Text("Description")
                    .padding(20)
            }
        }
    }
}
