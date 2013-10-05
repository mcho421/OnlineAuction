dropdb test
createdb test
psql -f OnlineAuction.sql test
psql -f OnlineAuctionSampleEntries.sql test
