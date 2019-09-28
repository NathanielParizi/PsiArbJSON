//import android.util.Log;
//
//import java.math.BigDecimal;
//

public class PairMatchingComponent {


    //
//     for (int a = 0; a < baseTokensLiqArr.length; a++) {
//
//        c1 = pairTokensLiquid[a];
//        c1Base = baseTokensLiqArr[a];
//        c1Quote = quoteTokensLiqArr[a];
//
//        for (int b = 0; b < baseTokensLiqArr.length; b++) {
//
//            c2 = pairTokensLiquid[b];
//            c2Base = baseTokensLiqArr[b];
//            c2Quote = quoteTokensLiqArr[b];
//
//            for (int c = 0; c < baseTokensLiqArr.length; c++) {
//
//                c3 = pairTokensLiquid[c];
//                c3Base = baseTokensLiqArr[c];
//                c3Quote = quoteTokensLiqArr[c];
//
//
//                z++;
//
//                // Compare base currency with quote currency to secure round-trip triangular arbitrage
//                if (
//                        (  // Path 1
//                                (c1Quote.equals(c2Base) && c2Quote.equals(c3Base))
//                                        && (c1Base.equals(c3Quote)))
//
//                                ||
//                                (   // Path 2
//                                        (c1Quote.equals(c2Base) && c2Quote.equals(c3Quote))
//                                                && (c1Base.equals(c3Base)))
//
//                                ||
//                                (   // Path 3
//                                        (c1Quote.equals(c2Quote) && c2Base.equals(c3Base))
//                                                && (c1Base.equals(c3Quote)))
//
//                                ||
//                                (// Path 4
//                                        (c1Quote.equals(c2Quote) && c2Base.equals(c3Quote))
//                                                && (c1Base.equals(c3Base)))
//
//                                ||
//                                (   // Path 5
//                                        (c1Base.equals(c2Base) && c2Quote.equals(c3Base))
//                                                && (c1Quote.equals(c3Quote)))
//                                ||
//
//                                (   // Path 6
//                                        (c1Base.equals(c2Base) && c2Quote.equals(c3Quote))
//                                                && (c1Quote.equals(c3Base)))
//
//                                ||
//                                (   // Path 7
//                                        (c1Base.equals(c2Quote) && c2Base.equals(c3Base))
//                                                && (c1Quote.equals(c3Quote)))
//
//                                ||
//                                (   // Path 8
//                                        (c1Base.equals(c2Quote) && c2Base.equals(c3Quote))
//                                                && (c1Quote.equals(c3Base)))
//                ) {
//
//
//                    //  arbChain.add(c1 + " " + c2 + " " + c3);
//
//
//
//
//                    //   Log.d("CHAINS", "[" + exchange[chainIndex[a]] + "] [" + exchange[chainIndex[b]] + "] [" + exchange[chainIndex[c]] + "]");
//                    Log.d("CHAINS", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]] + " " + a + " " + b + " " + " " + c);//                        Log.d("ArbChain", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);
////                        Log.d("CHAINS", "C1: [" + bidTokens[chainIndex[a]] + "]\t C2: [" + bidTokens[chainIndex[b]] + "]\t C3: [" + bidTokens[chainIndex[c]]);
////                        Log.d("CHAINS", "C1: [" + askTokens[chainIndex[a]] + "]\t C2:[" + askTokens[chainIndex[b]] + "]\t C3: [" + askTokens[chainIndex[c]]);
////
//
//
//
//                    // BID / ASK RATES for 3 pairs *****************
//                    cd1b = BigDecimal.valueOf((quickChainC1[0]));   // C1 Bid
//                    cd1a = askTokens[chainIndex[a]];   // C1 Ask
//                    cd2b = bidTokens[chainIndex[b]];   // C2 Bid
//                    cd2a = askTokens[chainIndex[b]];   // C2 Ask
//                    cd3b = bidTokens[chainIndex[c]];   // C3 Bid
//                    cd3a = askTokens[chainIndex[c]];   // C3 Ask
//                    //**********************************************
//
//
//                    // Calculate triangular arbitrage
//
////                        Log.d("DATACLEAN_PATH_4", "[" + c1 + "] [" + c2 + "] [" + c3 + "]");
////                        Log.d("DATACLEAN", "[" + exchange[chainIndex[a]] + "] [" + exchange[chainIndex[b]] + "] [" + exchange[chainIndex[c]] + "]");
////                        Log.d("DATACLEAN", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);//                        Log.d("ArbChain", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);
////                        Log.d("DATACLEAN", "C1: [" + bidTokens[chainIndex[a]] + "]\t C2: [" + bidTokens[chainIndex[b]] + "]\t C3: [" + bidTokens[chainIndex[c]]);
////                        Log.d("DATACLEAN", "C1: [" + askTokens[chainIndex[a]] + "]\t C2:[" + askTokens[chainIndex[b]] + "]\t C3: [" + askTokens[chainIndex[c]]);
//
//
//
                }
