//
//  Title.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 22/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Title : View {
    let title: String
    var subtitle: String = ""
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .bold()
                .font(.system(size: 22))
            
            if (!subtitle.isEmpty) {
                Text(subtitle)
                    .font(.system(size: 16))
            }
        }
    }
}
