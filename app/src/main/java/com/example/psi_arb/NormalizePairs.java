package com.example.psi_arb;

//
//*****************************************************************
// This class is responsible for normalizing currency pair data.
// Cryptocurrency Exchanges customize their own acronyms of
// specific coins and in order to calculate arbitrage from all
// pairs, we need these acronyms to match. This class will do such
// operations.
//*****************************************************************

import static com.example.psi_arb.MainActivity.poloniexPair;

public class NormalizePairs {

    private static String pair;
    private static String[] binancePair = new String[1000];

    public static String getPair() {
        return pair;
    }

    public static void setPair(String pair) {
        NormalizePairs.pair = pair;
    }

    private static String base;
    private static String quote;

    public NormalizePairs() {
    }

    public static String getBase() {
        return base;
    }

    public static void setBase(String base) {
        NormalizePairs.base = base;
    }

    public static String getQuote() {
        return quote;
    }

    public static void setQuote(String quote) {
        NormalizePairs.quote = quote;
    }


    public static void normalize(String str, int i) {
        switch (str) {

            case "QTUMETH":
                binancePair[i] = "QTUM_ETH";
                break;
            case "BTCUSDT":
                binancePair[i] = "BTC_USDT";
                break;
            case "ETHUSDT":
                binancePair[i] = "ETH_USDT";
                break;
            case "QTUMBTC":
                binancePair[i] = "QTUM_BTC";
                break;
            case "YOYOBTC":
                binancePair[i] = "YOYO_BTC";
                break;
            case "STRATBTC":
                binancePair[i] = "STRAT_BTC";
                break;
            case "STRATETH":
                binancePair[i] = "STRAT_ETH";
                break;
            case "SNGLSBTC":
                binancePair[i] = "SNGLS_BTC";
                break;
            case "SNGLSETH":
                binancePair[i] = "SNGLS_ETH";
                break;
            case "IOTABTC":
                binancePair[i] = "IOTA_BTC";
                break;
            case "IOTAETH":
                binancePair[i] = "IOTA_ETH";
                break;
            case "LINKBTC":
                binancePair[i] = "LINK_BTC";
                break;
            case "LINKETH":
                binancePair[i] = "LINK_ETH";
                break;
            case "SALTBTC":
                binancePair[i] = "SALT_BTC";
                break;
            case "SALTETH":
                binancePair[i] = "SALT_ETH";
                break;
            case "DASHBTC":
                binancePair[i] = "DASH_BTC";
                break;
            case "DASHETH":
                binancePair[i] = "DASH_ETH";
                break;
            case "POWRBTC":
                binancePair[i] = "POWR_BTC";
                break;
            case "POWRETH":
                binancePair[i] = "POWR_ETH";
                break;
            case "YOYOETH":
                binancePair[i] = "YOYO_ETH";
                break;
            case "STORJBTC":
                binancePair[i] = "STORJ_BTC";
                break;
            case "STORJETH":
                binancePair[i] = "STORJ_ETH";
                break;
            case "BNBUSDT":
                binancePair[i] = "BNB_USDT";
                break;
            case "YOYOBNB":
                binancePair[i] = "YOYO_BNB";
                break;
            case "POWRBNB":
                binancePair[i] = "POWR_BNB";
                break;
            case "NULSBNB":
                binancePair[i] = "NULS_BNB";
                break;
            case "NULSBTC":
                binancePair[i] = "NULS_BTC";
                break;
            case "NULSETH":
                binancePair[i] = "NULS_ETH";
                break;
            case "BCCUSDT":
                binancePair[i] = "BCC_USDT";
                break;
            case "BCPTBTC":
                binancePair[i] = "BCPT_BTC";
                break;
            case "BCPTETH":
                binancePair[i] = "BCPT_ETH";
                break;
            case "BCPTBNB":
                binancePair[i] = "BCPT_BNB";
                break;
            case "NEOUSDT":
                binancePair[i] = "NEO_USDT";
                break;
            case "FUELBTC":
                binancePair[i] = "FUEL_BTC";
                break;
            case "FUELETH":
                binancePair[i] = "FUEL_ETH";
                break;
            case "MANABTC":
                binancePair[i] = "MANA_BTC";
                break;
            case "MANAETH":
                binancePair[i] = "MANA_ETH";
                break;
            case "IOTABNB":
                binancePair[i] = "IOTA_BNB";
                break;
            case "LENDBTC":
                binancePair[i] = "LEND_BTC";
                break;
            case "LENDETH":
                binancePair[i] = "LEND_ETH";
                break;
            case "WABIBTC":
                binancePair[i] = "WABI_BTC";
                break;
            case "WABIETH":
                binancePair[i] = "WABI_ETH";
                break;
            case "WABIBNB":
                binancePair[i] = "WABI_BNB";
                break;
            case "LTCUSDT":
                binancePair[i] = "LTCU_SDT";
                break;
            case "WAVESBTC":
                binancePair[i] = "WAVES_BTC";
                break;
            case "WAVESETH":
                binancePair[i] = "WAVES_ETH";
                break;
            case "WAVESBNB":
                binancePair[i] = "WAVES_BNB";
                break;
            case "AIONBTC":
                binancePair[i] = "AION_BTC";
                break;
            case "AIONETH":
                binancePair[i] = "AION_ETH";
                break;
            case "AIONBNB":
                binancePair[i] = "AION_BNB";
                break;
            case "NEBLBTC":
                binancePair[i] = "NEBL_BTC";
                break;
            case "NEBLETH":
                binancePair[i] = "NEBL_ETH";
                break;
            case "NEBLBNB":
                binancePair[i] = "NEBL_BNB";
                break;
            case "WINGSBTC":
                binancePair[i] = "WINGS_BTC";
                break;
            case "WINGSETH":
                binancePair[i] = "WINGS_ETH";
                break;
            case "APPCBTC":
                binancePair[i] = "APPC_BTC";
                break;
            case "APPCETH":
                binancePair[i] = "APPC_ETH";
                break;
            case "APPCBNB":
                binancePair[i] = "APPC_BNB";
                break;
            case "VIBEBTC":
                binancePair[i] = "VIBE_BTC";
                break;
            case "VIBEETH":
                binancePair[i] = "VIBE_ETH";
                break;
            case "PIVXBTC":
                binancePair[i] = "PIVX_BTC";
                break;
            case "PIVXETH":
                binancePair[i] = "PIVX_ETH";
                break;
            case "PIVXBNB":
                binancePair[i] = "PIVX_BNB";
                break;
            case "IOSTBTC":
                binancePair[i] = "IOST_BTC";
                break;
            case "IOSTETH":
                binancePair[i] = "IOST_ETH";
                break;
            case "STEEMBTC":
                binancePair[i] = "STEEM_BTC";
                break;
            case "STEEMETH":
                binancePair[i] = "STEEM_ETH";
                break;
            case "STEEMBNB":
                binancePair[i] = "STEEM_BNB";
                break;
            case "NANOBTC":
                binancePair[i] = "NANO_BTC";
                break;
            case "NANOETH":
                binancePair[i] = "NANO_ETH";
                break;
            case "NANOBNB":
                binancePair[i] = "NANO_BNB";
                break;
            case "AEBTC":
                binancePair[i] = "AE_BTC";
                break;
            case "AEETH":
                binancePair[i] = "AE_ETH";
                break;
            case "AEBNB":
                binancePair[i] = "AE_BNB";
                break;
            case "NCASHBTC":
                binancePair[i] = "NCASH_BTC";
                break;
            case "NCASHETH":
                binancePair[i] = "NCASH_ETH";
                break;
            case "NCASHBNB":
                binancePair[i] = "NCASH_BNB";
                break;
            case "STORMBTC":
                binancePair[i] = "STORM_BTC";
                break;
            case "STORMETH":
                binancePair[i] = "STORM_ETH";
                break;
            case "STORMBNB":
                binancePair[i] = "STORM_BNB";
                break;
            case "QTUMUSDT":
                binancePair[i] = "QTUM_USDT";
                break;
            case "QTUMBNB":
                binancePair[i] = "QTUM_BNB";
                break;
            case "ADAUSDT":
                binancePair[i] = "ADA_USDT";
                break;
            case "LOOMBTC":
                binancePair[i] = "LOOM_BTC";
                break;
            case "LOOMETH":
                binancePair[i] = "LOOM_ETH";
                break;
            case "LOOMBNB":
                binancePair[i] = "LOOM_BNB";
                break;
            case "XRPUSDT":
                binancePair[i] = "XRP_USDT";
                break;
            case "BTCTUSD":
                binancePair[i] = "BTC_TUSD";
                break;
            case "ETHTUSD":
                binancePair[i] = "ETH_TUSD";
                break;
            case "EOSUSDT":
                binancePair[i] = "EOS_USDT";
                break;
            case "THETABTC":
                binancePair[i] = "THETA_BTC";
                break;
            case "THETAETH":
                binancePair[i] = "THETA_ETH";
                break;
            case "THETABNB":
                binancePair[i] = "THETA_BNB";
                break;
            case "TUSDUSDT":
                binancePair[i] = "TUSD_USDT";
                break;
            case "IOTAUSDT":
                binancePair[i] = "IOTA_USDT";
                break;
            case "XLMUSDT":
                binancePair[i] = "XLM_USDT";
                break;
            case "IOTXBTC":
                binancePair[i] = "IOTX_BTC";
                break;
            case "IOTXETH":
                binancePair[i] = "IOTX_ETH";
                break;
            case "DATABTC":
                binancePair[i] = "DATA_BTC";
                break;
            case "DATAETH":
                binancePair[i] = "DATA_ETH";
                break;
            case "ONTUSDT":
                binancePair[i] = "ON_TUSDT";
                break;
            case "TRXUSDT":
                binancePair[i] = "TRX_USDT";
                break;
            case "ETCUSDT":
                binancePair[i] = "ETC_USDT";
                break;
            case "ICXUSDT":
                binancePair[i] = "ICX_USDT";
                break;
            case "NPXSBTC":
                binancePair[i] = "NPXS_BTC";
                break;
            case "NPXSETH":
                binancePair[i] = "NPXS_ETH";
                break;
            case "VENUSDT":
                binancePair[i] = "VEN_USDT";
                break;
            case "DENTBTC":
                binancePair[i] = "DENT_BTC";
                break;
            case "DENTETH":
                binancePair[i] = "DENT_ETH";
                break;
            case "ARDRBTC":
                binancePair[i] = "ARDR_BTC";
                break;
            case "ARDRETH":
                binancePair[i] = "ARDR_ETH";
                break;
            case "ARDRBNB":
                binancePair[i] = "ARDR_BNB";
                break;
            case "NULSUSDT":
                binancePair[i] = "NULSU_SDT";
                break;
            case "VETUSDT":
                binancePair[i] = "VET_USDT";
                break;
            case "DOCKBTC":
                binancePair[i] = "DOCK_BTC";
                break;
            case "DOCKETH":
                binancePair[i] = "DOCK_ETH";
                break;
            case "POLYBTC":
                binancePair[i] = "POLY_BTC";
                break;
            case "POLYBNB":
                binancePair[i] = "POLY_BNB";
                break;
            case "HCBTC":
                binancePair[i] = "HC_BTC";
                break;
            case "HCETH":
                binancePair[i] = "HC_ETH";
                break;
            case "GOBTC":
                binancePair[i] = "GO_BTC";
                break;
            case "GOBNB":
                binancePair[i] = "GO_BNB";
                break;
            case "PAXUSDT":
                binancePair[i] = "PAX_USDT";
                break;
            case "MITHBTC":
                binancePair[i] = "MITH_BTC";
                break;
            case "MITHBNB":
                binancePair[i] = "MITH_BNB";
                break;
            case "BCHABCBTC":
                binancePair[i] = "BCHABC_BTC";
                break;
            case "BCHSVUSDT":
                binancePair[i] = "BCHSV_USDT";
                break;
            case "BCHABCUSDT":
                binancePair[i] = "BCHABC_USDT";
                break;
            case "BNBTUSD":
                binancePair[i] = "BNB_TUSD";
                break;
            case "XRPTUSD":
                binancePair[i] = "XRP_TUSD";
                break;
            case "EOSTUSD":
                binancePair[i] = "EOS_TUSD";
                break;
            case "XLMTUSD":
                binancePair[i] = "XLM_TUSD";
                break;
            case "BNBUSDC":
                binancePair[i] = "BNB_USDC";
                break;
            case "BTCUSDC":
                binancePair[i] = "BTC_USDC";
                break;
            case "ETHUSDC":
                binancePair[i] = "ETH_USDC";
                break;
            case "XRPUSDC":
                binancePair[i] = "XRP_USDC";
                break;
            case "EOSUSDC":
                binancePair[i] = "EOS_USDC";
                break;
            case "XLMUSDC":
                binancePair[i] = "XLM_USDC";
                break;
            case "USDCUSDT":
                binancePair[i] = "USD_CUSDT";
                break;
            case "ADATUSD":
                binancePair[i] = "ADA_TUSD";
                break;
            case "TRXTUSD":
                binancePair[i] = "TRX_TUSD";
                break;
            case "NEOTUSD":
                binancePair[i] = "NEO_TUSD";
                break;
            case "PAXTUSD":
                binancePair[i] = "PAX_TUSD";
                break;
            case "USDCTUSD":
                binancePair[i] = "USDC_TUSD";
                break;
            case "USDCPAX":
                binancePair[i] = "USD_CPAX";
                break;
            case "LINKUSDT":
                binancePair[i] = "LINK_USDT";
                break;
            case "LINKTUSD":
                binancePair[i] = "LINK_TUSD";
                break;
            case "LINKPAX":
                binancePair[i] = "LINK_PAX";
                break;
            case "LINKUSDC":
                binancePair[i] = "LINK_USDC";
                break;
            case "WAVESUSDT":
                binancePair[i] = "WAVES_USDT";
                break;
            case "WAVESTUSD":
                binancePair[i] = "WAVES_TUSD";
                break;
            case "WAVESPAX":
                binancePair[i] = "WAVES_PAX";
                break;
            case "WAVESUSDC":
                binancePair[i] = "WAVES_USDC";
                break;
            case "BCHABCTUSD":
                binancePair[i] = "BCHABC_TUSD";
                break;
            case "BCHABCPAX":
                binancePair[i] = "BCHABC_PAX";
                break;
            case "BCHABCUSDC":
                binancePair[i] = "BCHABC_USDC";
                break;
            case "LTCTUSD":
                binancePair[i] = "LTC_TUSD";
                break;
            case "LTCUSDC":
                binancePair[i] = "LTC_USDC";
                break;
            case "TRXUSDC":
                binancePair[i] = "TRX_USDC";
                break;
            case "BTTUSDT":
                binancePair[i] = "BTT_USDT";
                break;
            case "BNBUSDS":
                binancePair[i] = "BNB_USDS";
                break;
            case "BTCUSDS":
                binancePair[i] = "BTC_USDS";
                break;
            case "USDSUSDT":
                binancePair[i] = "USDS_USDT";
                break;
            case "USDSPAX":
                binancePair[i] = "USDS_PAX";
                break;
            case "USDSTUSD":
                binancePair[i] = "USDS_TUSD";
                break;
            case "USDSUSDC":
                binancePair[i] = "USDS_USDC";
                break;
            case "BTTTUSD":
                binancePair[i] = "BTT_TUSD";
                break;
            case "BTTUSDC":
                binancePair[i] = "BTT_USDC";
                break;
            case "ONGUSDT":
                binancePair[i] = "ONG_USDT";
                break;
            case "HOTUSDT":
                binancePair[i] = "HOT_USDT";
                break;
            case "ZILUSDT":
                binancePair[i] = "ZIL_USDT";
                break;
            case "ZRXUSDT":
                binancePair[i] = "ZRX_USDT";
                break;
            case "FETUSDT":
                binancePair[i] = "FET_USDT";
                break;
            case "BATUSDT":
                binancePair[i] = "BAT_USDT";
                break;
            case "XMRUSDT":
                binancePair[i] = "XMR_USDT";
                break;
            case "ZECUSDT":
                binancePair[i] = "ZEC_USDT";
                break;
            case "ZECTUSD":
                binancePair[i] = "ZEC_TUSD";
                break;
            case "ZECUSDC":
                binancePair[i] = "ZEC_USDC";
                break;
            case "IOSTBNB":
                binancePair[i] = "IOST_BNB";
                break;
            case "IOSTUSDT":
                binancePair[i] = "IOST_USDT";
                break;
            case "CELRBNB":
                binancePair[i] = "CELR_BNB";
                break;
            case "CELRBTC":
                binancePair[i] = "CELR_BTC";
                break;
            case "CELRUSDT":
                binancePair[i] = "CELR_USDT";
                break;
            case "ADAUSDC":
                binancePair[i] = "ADA_USDC";
                break;
            case "NEOPAX":
                binancePair[i] = "NEO_PAX";
                break;
            case "NEOUSDC":
                binancePair[i] = "NEO_USDC";
                break;
            case "DASHBNB":
                binancePair[i] = "DASH_BNB";
                break;
            case "DASHUSDT":
                binancePair[i] = "DASH_USDT";
                break;
            case "NANOUSDT":
                binancePair[i] = "NANO_USDT";
                break;
            case "OMGUSDT":
                binancePair[i] = "OMG_USDT";
                break;
            case "THETAUSDT":
                binancePair[i] = "THETA_USDT";
                break;
            case "ENJUSDT":
                binancePair[i] = "ENJ_USDT";
                break;
            case "MITHUSDT":
                binancePair[i] = "MITH_USDT";
                break;
            case "MATICBNB":
                binancePair[i] = "MATIC_BNB";
                break;
            case "MATICBTC":
                binancePair[i] = "MATIC_BTC";
                break;
            case "MATICUSDT":
                binancePair[i] = "MATIC_USDT";
                break;
            case "ATOMBNB":
                binancePair[i] = "ATOM_BNB";
                break;
            case "ATOMBTC":
                binancePair[i] = "ATOM_BTC";
                break;
            case "ATOMUSDT":
                binancePair[i] = "ATOM_USDT";
                break;
            case "ATOMUSDC":
                binancePair[i] = "ATOM_USDC";
                break;
            case "ATOMPAX":
                binancePair[i] = "ATOM_PAX";
                break;
            case "ATOMTUSD":
                binancePair[i] = "ATOM_TUSD";
                break;
            case "ETCUSDC":
                binancePair[i] = "ETC_USDC";
                break;
            case "ETCTUSD":
                binancePair[i] = "ETC_TUSD";
                break;
            case "BATUSDC":
                binancePair[i] = "BAT_USDC";
                break;
            case "BATTUSD":
                binancePair[i] = "BAT_TUSD";
                break;
            case "PHBUSDC":
                binancePair[i] = "PHB_USDC";
                break;
            case "PHBTUSD":
                binancePair[i] = "PHB_TUSD";
                break;
            case "TFUELBNB":
                binancePair[i] = "TFUEL_BNB";
                break;
            case "TFUELBTC":
                binancePair[i] = "TFUEL_BTC";
                break;
            case "TFUELUSDT":
                binancePair[i] = "TFUEL_USDT";
                break;
            case "TFUELUSDC":
                binancePair[i] = "TFUEL_USDC";
                break;
            case "TFUELTUSD":
                binancePair[i] = "TFUEL_TUSD";
                break;
            case "TFUELPAX":
                binancePair[i] = "TFUEL_PAX";
                break;
            case "ONEUSDT":
                binancePair[i] = "ONE_USDT";
                break;
            case "ONETUSD":
                binancePair[i] = "ONET_USD";
                break;
            case "ONEUSDC":
                binancePair[i] = "ONE_USDC";
                break;
            case "FTMUSDT":
                binancePair[i] = "FTM_USDT";
                break;
            case "FTMTUSD":
                binancePair[i] = "FTM_TUSD";
                break;
            case "FTMUSDC":
                binancePair[i] = "FTM_USDC";
                break;
            case "BTCBBTC":
                binancePair[i] = "BTCB_BTC";
                break;
            case "BCPTTUSD":
                binancePair[i] = "BCPT_TUSD";
                break;
            case "BCPTPAX":
                binancePair[i] = "BCPT_PAX";
                break;
            case "BCPTUSDC":
                binancePair[i] = "BCPT_USDC";
                break;
            case "ALGOBNB":
                binancePair[i] = "ALGO_BNB";
                break;
            case "ALGOBTC":
                binancePair[i] = "ALGO_BTC";
                break;
            case "ALGOUSDT":
                binancePair[i] = "ALGO_USDT";
                break;
            case "ALGOTUSD":
                binancePair[i] = "ALGO_TUSD";
                break;
            case "ALGOPAX":
                binancePair[i] = "ALGO_PAX";
                break;
            case "ALGOUSDC":
                binancePair[i] = "ALGO_USDC";
                break;
            case "USDSBUSDT":
                binancePair[i] = "USDSB_USDT";
                break;
            case "USDSBUSDS":
                binancePair[i] = "USDSB_USDS";
                break;
            case "GTOUSDT":
                binancePair[i] = "GTO_USDT";
                break;
            case "GTOTUSD":
                binancePair[i] = "GTOT_USD";
                break;
            case "GTOUSDC":
                binancePair[i] = "GTO_USDC";
                break;
            case "ERDUSDT":
                binancePair[i] = "ERD_USDT";
                break;
            case "ERDUSDC":
                binancePair[i] = "ERD_USDC";
                break;
            case "DOGEBNB":
                binancePair[i] = "DOGE_BNB";
                break;
            case "DOGEBTC":
                binancePair[i] = "DOGE_BTC";
                break;
            case "DOGEUSDT":
                binancePair[i] = "DOGE_USDT";
                break;
            case "DOGEPAX":
                binancePair[i] = "DOGE_PAX";
                break;
            case "DOGEUSDC":
                binancePair[i] = "DOGE_USDC";
                break;
            case "DUSKBNB":
                binancePair[i] = "DUSK_BNB";
                break;
            case "DUSKBTC":
                binancePair[i] = "DUSK_BTC";
                break;
            case "DUSKUSDT":
                binancePair[i] = "DUSK_USDT";
                break;
            case "DUSKUSDC":
                binancePair[i] = "DUSK_USDC";
                break;
            case "DUSKPAX":
                binancePair[i] = "DUSK_PAX";
                break;
            case "BGBPUSDC":
                binancePair[i] = "BGBP_USDC";
                break;
            case "ANKRBNB":
                binancePair[i] = "ANKR_BNB";
                break;
            case "ANKRBTC":
                binancePair[i] = "ANKR_BTC";
                break;
            case "ANKRUSDT":
                binancePair[i] = "ANKR_USDT";
                break;
            case "ANKRTUSD":
                binancePair[i] = "ANKR_TUSD";
                break;
            case "ANKRPAX":
                binancePair[i] = "ANKR_PAX";
                break;
            case "ANKRUSDC":
                binancePair[i] = "ANKR_USDC";
                break;
            case "ONTUSDC":
                binancePair[i] = "ONT_USDC";
                break;
            case "WINUSDT":
                binancePair[i] = "WIN_USDT";
                break;
            case "WINUSDC":
                binancePair[i] = "WIN_USDC";
                break;
            case "COSUSDT":
                binancePair[i] = "COS_USDT";
                break;
            case "TUSDBTUSD":
                binancePair[i] = "TUSDB_TUSD";
                break;
            case "NPXSUSDT":
                binancePair[i] = "NPXS_USDT";
                break;
            case "NPXSUSDC":
                binancePair[i] = "NPXS_USDC";
                break;
            case "COCOSBNB":
                binancePair[i] = "COCOS_BNB";
                break;
            case "COCOSBTC":
                binancePair[i] = "COCOS_BTC";
                break;
            case "COCOSUSDT":
                binancePair[i] = "COCOS_USDT";
                break;
            case "MTLUSDT":
                binancePair[i] = "MTL_USDT";
                break;
            case "TOMOBNB":
                binancePair[i] = "TOMO_BNB";
                break;
            case "TOMOBTC":
                binancePair[i] = "TOMO_BTC";
                break;
            case "TOMOUSDT":
                binancePair[i] = "TOMO_USDT";
                break;
            case "TOMOUSDC":
                binancePair[i] = "TOMO_USDC";
                break;
            case "PERLBNB":
                binancePair[i] = "PERL_BNB";
                break;
            case "PERLBTC":
                binancePair[i] = "PERL_BTC";
                break;
            case "PERLUSDC":
                binancePair[i] = "PERL_USDC";
                break;
            case "PERLUSDT":
                binancePair[i] = "PERL_USDT";
                break;
            case "DENTUSDT":
                binancePair[i] = "DENT_USDT";
                break;
            case "MFTUSDT":
                binancePair[i] = "MFT_USDT";
                break;
            case "KEYUSDT":
                binancePair[i] = "KEY_USDT";
                break;
            case "STORMUSDT":
                binancePair[i] = "STORM_USDT";
                break;
            case "DOCKUSDT":
                binancePair[i] = "DOCK_USDT";
                break;
            case "WANUSDT":
                binancePair[i] = "WAN_USDT";
                break;
            case "FUNUSDT":
                binancePair[i] = "FUN_USDT";
                break;
            case "CVCUSDT":
                binancePair[i] = "CVC_USDT";
                break;


        }


    }

    public static void normalizePoloniex(String str, int i){

        if(str.equals("BTC_EOS")){
            poloniexPair[i] = "EOS_BTC";
        }
        if(str.equals("ETH_EOS")){
            poloniexPair[i] = "EOS_ETH";
        }


    }
}
