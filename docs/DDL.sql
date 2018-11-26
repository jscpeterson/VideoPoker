CREATE TABLE IF NOT EXISTS `Paytable` (
    `paytable_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
    `name`        TEXT COLLATE NOCASE
);

CREATE TABLE IF NOT EXISTS `PokerHand` (
    `poker_hand_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
    `paytable_id`   INTEGER NOT NULL
    `name`          TEXT
    `ruleSequence`  TEXT
    `betOneValue`   INTEGER NOT NULL
    `betFiveValue`  INTEGER NOT NULL
    `showInTable`   INTEGER NOT NULL
    FOREIGN KEY(`paytable_id`) REFERENCES `Paytable`(`paytable_id`) ON UPDATE NO ACTION ON DELETE NO ACTION 
);
        
CREATE INDEX `index_PokerHand_paytable_id` ON `PokerHand` (`paytable_id`)
