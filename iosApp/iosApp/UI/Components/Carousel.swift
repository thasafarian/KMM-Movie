//
//  Carousel.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 22/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct Carousel<Content: View,T: Hashable>: View {
    var content: (T) -> Content
    var list: [T]
    
    var spacing: CGFloat
    var trailingSpace: CGFloat
    @Binding var index: Int
    
    var carouselImageHeight: CGFloat
    var isHasCaption: Bool
    
    init(spacing: CGFloat = 15,
         trailingSpace: CGFloat = 100,
         index: Binding<Int>,
         items: [T],
         carouselImageHeight: CGFloat,
         isHasCaption: Bool,
         @ViewBuilder content: @escaping (T)->Content) {
        
        self.list = items
        self.spacing = spacing
        self.trailingSpace = trailingSpace
        self._index = index
        self.content = content
        self.carouselImageHeight = carouselImageHeight
        self.isHasCaption = isHasCaption
    }
    
    @GestureState var offset: CGFloat = 0
    @State var currentIndex: Int = 0
    
    var body: some View{
        GeometryReader{ proxy in
            let width = proxy.size.width - (trailingSpace - spacing)
            let adjustMentWidth = (trailingSpace / 2) - spacing
            let height = (proxy.size.height/2)
            HStack(spacing: spacing){
                ForEach(list, id: \.self){item in
                    content(item)
                        .frame(width: proxy.size.width - trailingSpace, height: carouselImageHeight)
                }
            }.onAppear() {
                print("carousel image height : \(carouselImageHeight)")

                print("carousel proxy height : \( proxy.size.height)")
                print("carousel proxy width : \( proxy.size.width)")
                print("carousel width : \(width)")
                
            }
            .padding(.horizontal,spacing)
            .offset(x: (CGFloat(currentIndex) * -width) + (currentIndex != 0 ? adjustMentWidth : 0) + offset)
            .gesture(
                DragGesture()
                    .updating($offset, body: { value, out, _ in
                        out = value.translation.width
                    })
                    .onEnded({ value in
                        let offsetX = value.translation.width
                        let progress = -offsetX / width
                        let roundIndex = progress.rounded()
                        currentIndex = index
                        
                        let xOffset = (CGFloat(currentIndex) * -width) + (currentIndex != 0 ? adjustMentWidth : 0) + offset
                        print("carousel x offset : \(xOffset)")
                    })
                    .onChanged({ value in
                        let offsetX = value.translation.width
                        let progress = -offsetX / width
                        let roundIndex = progress.rounded()
                        index = max(min(currentIndex + Int(roundIndex), list.count - 1), 0)
                    })
            )
            
            // Movie caption
            if list.count > 0 && isHasCaption {
                if let movies = list as? [Movie] {
                    VStack {
                        Text(movies[currentIndex].title)
                            .bold()
                            .font(.system(size: 20))
                        
                        Text(movies[currentIndex].description_)
                            .lineLimit(4)
                            .font(.system(size: 16))
                    }
                    .padding(.all, 8)
                    .frame(
                        minWidth: 0,
                        maxWidth: .infinity,
                        minHeight:  (carouselImageHeight * 0.3),
                        maxHeight: (carouselImageHeight * 0.3)
                    )
                    .background(Color.gray.opacity(0.8))
                    .cornerRadius(16)
                    .offset(y: carouselImageHeight * (0.9))
                }
            }
        }
        .animation(.easeInOut, value: offset == 0)
    }
}
