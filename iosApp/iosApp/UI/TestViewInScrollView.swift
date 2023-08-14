//
//  TestViewInScrollView.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 28/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct TestSomeViewFillInScrollView: View {
    var body: some View {
        ContentView()
    }

    struct ContentView: View {
        @State private var height = CGFloat.zero

        var body: some View {
            NavigationView {
                VStack {
                    // Logo and postcode search
                    VStack {
                        Text("Logo")
                            .background(Color.red)

                        Text("Title")
                            .background(Color.blue)
                    }

                    .fixedSize(horizontal: false, vertical: true)
                    .padding()

                    GeometryReader { gp in
                        ScrollView(.vertical, showsIndicators: false) {
                            VStack(alignment: .leading) {

                                Text("Selector")
                                    .background(Color.yellow)

                                Text("Browse")
                                    .frame(minHeight: height, alignment: .top)
                                    .background(GeometryReader {
                                        Color.clear.preference(key: ViewOffsetKey.self,
                                                               value: $0.frame(in: .named("parent")).origin.y)
                                    })
                                    .background(Color.green)
                            }
                            .navigationBarHidden(true)
                        }
                        .coordinateSpace(name: "parent")
                        .onPreferenceChange(ViewOffsetKey.self) {
                            height = gp.size.height - $0
                        }
                        .frame(maxWidth: .infinity)
                    }
                }
                .background(Color.red)
            }
        }
    }
}

struct ViewOffsetKey: PreferenceKey {
    typealias Value = CGFloat
    static var defaultValue = CGFloat.zero
    static func reduce(value: inout Value, nextValue: () -> Value) {
        value += nextValue()
    }
}
