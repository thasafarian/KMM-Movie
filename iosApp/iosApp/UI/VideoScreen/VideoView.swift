//
//  VideoView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 21/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct VideoView: View {
    @StateObject var viewModel = MainViewModel()
    @State private var showDetail = false
    @State private var movieDesc = ""
    @State private var isLoaded = false
    
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack (alignment:.leading) {
                    LazyVGrid(columns: createGrid(), spacing: 16) {
                        ForEach(viewModel.topRated, id: \.self) { movie in
                            NavigationLink(value: movie) {
                                ZStack(alignment: .center) {
                                    RemoteImageView(
                                        url: URL(string: movie.imageUrl)!,
                                        placeholder: {
                                            Image("placeholder").frame(width: 40) // etc.
                                        },
                                        image: {
                                            $0.resizable()
                                                .resizable()
                                                .aspectRatio(contentMode: .fill)
                                        }
                                    )
                                    
                                    Image(systemName: "play.circle.fill")
                                        .resizable().aspectRatio(contentMode: .fit)
                                        .frame(width: 50, height: 50)
                                        .tint(Color.white)
                                }
                            }
                        }
                    }
                    .navigationDestination(for: Movie.self) { movie in
                        DetailView(movie: movie)
                    }
                    .navigationBarTitle("Videos")
                    .onAppear(){
                        Task{
                            if (!isLoaded) {
                                await viewModel.loadMovies(type: "top_rated")
                            }
                            isLoaded = viewModel.topRated.count > 0
                        }
                    }
                    .padding()
                }
            }
        }
    }
    
    func createGrid() -> [GridItem] {
        let columns = [
            GridItem(.fixed(100), spacing: 16),
            GridItem(.fixed(100), spacing: 16),
            GridItem(.fixed(100), spacing: 16)
        ]
        return columns
    }
}
