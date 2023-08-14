//
//  SearchBarView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 22/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct SearchBarView: View {
    @Binding var searchText: String
    var onSearchButtonClicked: (() -> Void)?
    
    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass") // Magnifying glass icon
                .foregroundColor(.gray)
                .padding(.leading, 8)
            
            TextField("",text: $searchText)
                .foregroundColor(.white)
                .placeholder(when: searchText.isEmpty) {
                    Text("Search").foregroundColor(.white)
                }
            
            if !searchText.isEmpty {
                Button(action: {
                    searchText = ""
                }) {
                    Image(systemName: "xmark.circle.fill") // Clear button icon
                        .foregroundColor(.gray)
                }
                .padding(.trailing, 8)
            }
            
            if let onSearchButtonClicked = onSearchButtonClicked {
                Button(action: {
                    onSearchButtonClicked()
                }) {
                    Text("Search")
                        .foregroundColor(.blue)
                }
                .padding(.trailing, 8)
            }
        }
        .padding(.vertical, 8)
        .background(Color.secondary.opacity(0.2))
        .cornerRadius(8)
    }
}
