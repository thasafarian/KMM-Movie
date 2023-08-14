//
//  SearchView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 21/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SearchView: View {
    @State private var searchText: String = ""
    @StateObject var viewModel = MainViewModel()
    @State var isLoaded = false
    
    var filteredMovie: [Movie] {
        if searchText.isEmpty {
            return viewModel.topRated
        } else {
            return viewModel.topRated.filter {
                $0.title.localizedCaseInsensitiveContains(searchText)
            }
        }
    }
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .leading) {
                SearchBarView(searchText: $searchText, onSearchButtonClicked: {
                    // Handle search action here
                    print("Search button clicked \(searchText)")
                })
                List {
                    ForEach(filteredMovie, id: \.self) { movie in
                        NavigationLink(value: movie) {
                            Text(movie.title)
                        }
                    }
                    
                    if viewModel.isFetchingDataCompleted == false {
                        ProgressView().onAppear() {
                            Task {await loadMore()}
                        }
                    }
                }
                .navigationDestination(for: Movie.self) { movie in
                    DetailView(movie: movie)
                }
                .navigationBarTitle("Find Movies").padding()
                Spacer()
            }
        }
    }
    
    private func loadMore() async {
        await viewModel.loadMovies(type: "top_rated")
    }
}
