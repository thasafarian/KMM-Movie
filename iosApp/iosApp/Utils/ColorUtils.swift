//
//  ColorUtils.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 22/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

func colorFromHexString(_ hexString: String) -> Color? {
    var hex = hexString.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
    if hex.hasPrefix("#") {
        hex.remove(at: hex.startIndex)
    }

    var rgbValue: UInt64 = 0
    Scanner(string: hex).scanHexInt64(&rgbValue)

    let red = Double((rgbValue & 0xFF0000) >> 16) / 255.0
    let green = Double((rgbValue & 0x00FF00) >> 8) / 255.0
    let blue = Double(rgbValue & 0x0000FF) / 255.0

    return Color(red: red, green: green, blue: blue)
}
