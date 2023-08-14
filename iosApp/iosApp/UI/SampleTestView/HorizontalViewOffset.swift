//
//  HorizontalViewOffset.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 28/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUIPager
import SwiftUI

struct HorizontalViewOffset: View {
    
    //@StateObject var page1 = Page.withIndex(0)
    @StateObject var page = Page.first()
    @State var data1 = Array(0..<7)
    @State var isPresented: Bool = false
    
    var body: some View {
        VStack(spacing: 10) {
            Pager(page: page,
                  data: data1,
                  id: \.self) { page in
                ZStack {
                    Rectangle()
                        .fill(Color.yellow)
                    Text("Page \(page)")
                }
                .cornerRadius(5)
                .shadow(radius: 5)
            }.sensitivity(.high)
                  .singlePagination(ratio: 0.33, sensitivity: .custom(0.2))
                  .preferredItemSize(CGSize(width: 300, height: 400))
                  .itemSpacing(10)
        }
    }
    
}
