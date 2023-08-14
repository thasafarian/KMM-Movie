//
//  NowPlayingView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 29/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SwiftUIPager
import shared

struct NowPlayingView : View {
    @StateObject var viewModel = MainViewModel()
    
    @StateObject var page = Page.first()
    @State var isPresented: Bool = false
    @State var nowPlayingIndex = 0
    var proxy: GeometryProxy
    
    var body: some View {
        ZStack(alignment: .bottom) {
            Pager(page: page,
                  data: viewModel.nowPlayingMovies,
                  id: \.self) { movie in
                ZStack {
                    NavigationLink(destination: DetailView(movie: movie)) {
                        AsyncImage(
                            url: URL(string : movie.imageUrl),
                            content: { image in
                                image.resizable()
                                    .aspectRatio(contentMode: .fit)
                            },
                            placeholder: {
                                ProgressView()
                            }
                        )
                    }
                }
            }.onPageChanged({ index in
                nowPlayingIndex = index
            })
            .pagingPriority(.simultaneous)
            .interactive(scale: 0.8)
            .singlePagination(ratio: 0.6, sensitivity: .custom(0.2))
            .preferredItemSize(CGSize(width: 300, height: 400))
            .frame(width: proxy.size.width, height: 400)
            
            if viewModel.nowPlayingMovies.count > 0 {
                let movies = viewModel.nowPlayingMovies
                VStack {
                    Text(movies[nowPlayingIndex].title)
                        .bold()
                        .font(.system(size: 20))
                    
                    Text(movies[nowPlayingIndex].description_)
                        .lineLimit(4)
                        .font(.system(size: 16))
                }
                .padding(.all, 8)
                .frame(
                    minWidth: 0,
                    maxWidth: proxy.size.width
                )
                .background(Color.black.opacity(0.8))
                .cornerRadius(16)
            }
        }.onAppear() {
            Task {
                await viewModel.loadMovies(type:"now_playing")
            }
        }
    }
}
