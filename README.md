# Trade Reporting
Sample data represents the instructions sent by various clients to JP Morgan to execute in the international market.

    A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where
    the work week starts Sunday and ends Thursday. No other holidays to be taken into account.

    A trade can only be settled on a working day.

    If an instructed settlement date falls on a weekend, then the settlement date should be changed to
    the next working day.

    USD amount of a trade = Price per unit * Units * Agreed Fx

Requirements
Create a report that shows

    Amount in USD settled incoming everyday

    Amount in USD settled outgoing everyday

    Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest
    amount for a buy instruction, then foo is rank 1 for outgoing

Sample Input:

    Entity,Buy/Sell,AgreedFx,Currency,InstructionDate,SettlementDate,Units,Price per unit
    foo,B,0.5,SGP,01 Jun 2016,02 Jun 2018,200,100.25
    foo,B,0.22,AED,05 Jun 2016,07 Jun 2018,450,150.5
    foo1,B,0.5,SGP,01 Jun 2016,02 Jun 2018,200,100.25
    xyz,B,0.5,SGP,01 Jun 2016,02 Jun 2018,100,100.25
    bar,S,0.22,AED,05 Jun 2016,07 Jun 2018,450,150.5
    bar1,S,0.22,AED,05 Jun 2016,07 Jun 2018,450,150.5
    thomascook,B,0.35,SAR,05 Jun 2016,08 Jun 2018,40,150.5
    thomascook,B,0.35,SAR,05 Jun 2016,10 Jun 2018,10,160
    lloyds,B,12,USD,02 Jul 2018,02 Jul 2018,50,110
    E1,B,0.128,HKD,02 Jul 2018,02 Jul 2018,100,20
    E1,S,0.128,HKD,02 Jul 2018,02 Jul 2018,110,5
    E2,B,0.325,JPY,02 Jul 2018,02 Jul 2018,10,220
    E2,S,0.325,JPY,02 Jul 2018,02 Jul 2018,3,250
    E3,B,0.078,CNY,02 Jul 2018,02 Jul 2018,220,12
    E3,S,0.078,CNY,02 Jul 2018,02 Jul 2018,200,14
    E4,B,0.523,MXN,02 Jul 2018,02 Jul 2018,100,23
    E4,S,0.523,MXN,02 Jul 2018,02 Jul 2018,150,25

Sample Output:

    Amount in USD settled outgoing(Buy) everyday
    Date                  TotalAmountInUSD
    2018-06-10                  2667.0000
    2018-06-07                 14899.5000
    2018-06-04                 25062.5000
    2018-07-02                  7879.8200

    Amount in USD settled incoming(Sell) everyday
    Date                  TotalAmountInUSD
    2018-06-07                 29799.0000
    2018-07-02                  2493.8000

    Ranking of entities based on outgoing(Buy) amount
    Date        Entity                Rank  TotalAmountInUSD         
    2018-06-10  thomascook            1           2667.0000   
    2018-06-07  foo                   1          14899.5000   
    2018-06-04  foo                   1          10025.0000   
    2018-06-04  foo1                  1          10025.0000   
    2018-06-04  xyz                   2           5012.5000   
    2018-07-02  lloyds                1           5500.0000   
    2018-07-02  E4                    2           1202.9000   
    2018-07-02  E2                    3            715.0000   
    2018-07-02  E1                    4            256.0000   
    2018-07-02  E3                    5            205.9200   

    Ranking of entities based on incoming(Sell) amount
    Date        Entity                Rank  TotalAmountInUSD         
    2018-06-07  bar1                  1          14899.5000   
    2018-06-07  bar                   1          14899.5000   
    2018-07-02  E4                    1           1961.2500   
    2018-07-02  E2                    2            243.7500   
    2018-07-02  E3                    3            218.4000   
    2018-07-02  E1                    4             70.4000  
    
    
