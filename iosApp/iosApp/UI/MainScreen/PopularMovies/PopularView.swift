//
//  PopularView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 27/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SwiftUIPager
import shared

struct PopularView: View {
    @StateObject var viewModel = MainViewModel()
    
    @StateObject var page = Page.first()
    @State var nowPlayingIndex = 0
    var proxy: GeometryProxy
    
    var body: some View {
        Pager(page: page,
              data: viewModel.popularMovies,
              id: \.self) { movie in
            ZStack {
                NavigationLink(destination: DetailView(movie: movie)) {
                    AsyncImage(
                        url: URL(string : movie.landscapeImageUrl),
                        content: { image in
                            image.resizable()
                                .aspectRatio(contentMode: .fit)
                        },
                        placeholder: {
                            ProgressView()
                        }
                    )
                }
                
                Image(systemName: "play.circle.fill")
                    .resizable().aspectRatio(contentMode: .fit)
                    .frame(width: 40, height: 40)
                    .tint(Color.white)
            }
        }
              .pagingPriority(.simultaneous)
              .itemSpacing(10)
              .singlePagination(ratio: 0.6, sensitivity: .custom(0.2))
              .preferredItemSize(CGSize(width: 250, height: 185))
              .alignment(.start)
              .frame(width: proxy.size.width, height: 200)
              .onAppear() {
                  Task {
                      await viewModel.loadMovies(type:"popular")
                  }
              }
    }
}
