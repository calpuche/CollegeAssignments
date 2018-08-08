//
//  TableViewCell.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/4/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class TableViewCell: UITableViewCell {

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    @IBOutlet weak var lblPlace: UILabel!
    @IBOutlet weak var lblDate: UILabel!

}
