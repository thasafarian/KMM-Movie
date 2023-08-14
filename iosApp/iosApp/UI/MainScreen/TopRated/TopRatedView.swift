//
//  TopRatedView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 30/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct TopRatedView: View {
    
    @StateObject var viewModel = MainViewModel()
    @State private var isLoaded = false
    var proxy: GeometryProxy
    
    var body: some View {
        VStack (alignment:.leading) {
            LazyVGrid(columns: createGrid(), spacing: 16) {
                ForEach(viewModel.topRated, id: \.self) { movie in
                    ZStack(alignment: .center) {
                        NavigationLink(destination: DetailView(movie: movie)) {
                            RemoteImageView(
                                url: URL(string: movie.imageUrl)!,
                                placeholder: {
                                    //Image("placeholder").frame(width: 40) // etc.
                                },
                                image: {
                                    $0.resizable()
                                        .resizable()
                                        .aspectRatio(contentMode: .fill)
                                }
                            )
                        }
                        Image(systemName: "play.circle.fill")
                            .resizable().aspectRatio(contentMode: .fit)
                            .frame(width: 40, height: 40)
                            .tint(Color.white)
                    }
                }
            }
            
            .onAppear() {
                hasBeenLoaded()
            }
            .padding()
        }
    }
    
    func createGrid() -> [GridItem] {
        let columns = [
            GridItem(.fixed(proxy.size.width/2), spacing: 16),
            GridItem(.fixed(proxy.size.width/2), spacing: 16)
        ]
        return columns
    }
    
    func hasBeenLoaded() {
        Task{
            if (!isLoaded) {
                await viewModel.loadMovies(type: "top_rated")
            }
            isLoaded = viewModel.topRated.count > 0
        }
    }
    
}
