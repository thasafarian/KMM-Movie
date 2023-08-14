//
//  DetailView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 25/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct DetailView: View {
    @Namespace var namespace
    var movie: Movie
    @State private var flag: Bool = true

    var body: some View {
        VStack(spacing: 20) {
            if flag {
                AsyncImage(url: URL(string: movie.imageUrl)) { image in
                    image.resizable().frame(width: 200, height: 320)
                } placeholder: {
                    ProgressView()
                }.matchedGeometryEffect(id: "playmovieeffect", in: namespace)
            }
            
            if !flag {
                DetailHeroAnimationView(
                    namespace: namespace,
                    imageUrl: movie.imageUrl
                )
            }
            
            Button(action: {
                print("playing movie")
                withAnimation(.spring()) {
                    flag.toggle()
                }
            }) {
                HStack {
                    if flag {
                        Image(systemName: "play")
                        Text("Play Trailer")
                    } else {
                        Image(systemName: "stop.fill")
                        Text("Stop Playing")
                    }
                }
            }
            .buttonStyle(GrowingButton())
            
            Text(movie.description_)
                .padding(20)
            
        }
    }
}
