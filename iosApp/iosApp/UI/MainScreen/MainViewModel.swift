//
//  MainViewModel.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 21/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@MainActor class MainViewModel: ObservableObject {
    
    private let getMoviesUseCase = MovieUseCase.init()
    
    @Published private(set) var nowPlayingMovies:[Movie] = []
    @Published private(set) var popularMovies:[Movie] = []
    @Published private(set) var topRated:[Movie] = []
    @Published private(set) var isFetchingDataCompleted: Bool = false

    
    private var currentPage = 1
    private var perPage = 20
    private(set) var loadFinished: Bool = false
    
    func loadMovies(type: String) async {

        
        do {
            let movies = try await getMoviesUseCase.invoke(
                forceUpdate: true,
                page: Int32(currentPage),
                type: type
            )
            loadFinished = movies.isEmpty
           
            currentPage += 1
            
            if movies.count < perPage {
                isFetchingDataCompleted = true
            }
            
            if type == "now_playing" {
                self.nowPlayingMovies = self.nowPlayingMovies + movies
            } else if  type == "popular" {
                self.popularMovies = self.popularMovies + movies
            } else if (type == "top_rated") {
                self.topRated = self.topRated + movies
            }
            
        } catch {
            let nsError = error as NSError
            print(nsError.localizedDescription)
            isFetchingDataCompleted = false
            loadFinished = true
            
            print(error.localizedDescription)
        }
    }
}

