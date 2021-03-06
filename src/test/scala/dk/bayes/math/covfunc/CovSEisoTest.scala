package dk.bayes.math.covfunc

import org.junit._
import org.junit.Assert._
import scala.math._
import scala.util.Random
import breeze.linalg.DenseMatrix
import dk.bayes.math.covfunc.CovSEiso

class CovSEisoTest {

  private val covFunc = new CovSEiso(sf = log(2), log(10))

  /**
   * F
   * Tests for cov()
   */

  @Test def test_1D_cov = {

    assertEquals(4, covFunc.cov(Array(3d), Array(3d)), 0.0001)
    assertEquals(3.8239, covFunc.cov(Array(2d), Array(5d)), 0.0001)
    assertEquals(2.9045, covFunc.cov(Array(2d), Array(10d)), 0.0001)
    assertEquals(1.3181, new CovSEiso(sf = log(2), log(200d)).cov(Array(2d), Array(300d)), 0.0001)
  }

  @Test def multi_dim_cov = {

    val x1 = Array.fill(4)(1d)
    val x2 = Array.fill(4)(2d)

    val covValue = covFunc.cov(x1, x2)
    assertEquals(3.92079, covValue, 0.00001)
  }

  @Test def multi_dim_cov_2 = {
    val rand = new Random(4656)
    val n = 2000
    val x1 = Array.fill(n)(rand.nextDouble)
    val x2 = Array.fill(n)(rand.nextDouble)

    val covValue = covFunc.cov(x1, x2)
    assertEquals(0.71287, covValue, 0.00001)
  }

  @Test def perf_test_1d_cov = {

    val x1 = Array(10d)
    val x2 = Array(2d)
    (1L to 2000L * 50 * 50).foreach(_ => covFunc.cov(x1, x2))

  }

  /**
   * Tests for df_dSf
   *
   */
  @Test def test_1D_df_dSf = {

    assertEquals(8, covFunc.df_dSf(Array(3d), Array(3d)), 0.0001)
    assertEquals(7.6479, covFunc.df_dSf(Array(2d), Array(5d)), 0.0001)
    assertEquals(5.8091, covFunc.df_dSf(Array(2d), Array(10d)), 0.0001)
    assertEquals(2.6363, new CovSEiso(sf = log(2), log(200)).df_dSf(Array(2d), Array(300d)), 0.0001)
  }

  @Test def multi_dim_df_dSf = {
    val rand = new Random(4656)
    val n = 2000
    val x1 = Array.fill(n)(rand.nextDouble)
    val x2 = Array.fill(n)(rand.nextDouble)

    val covValue = covFunc.df_dSf(x1, x2)
    assertEquals(1.4257, covValue, 0.0001)
  }

  @Test def perf_test_1d_df_dSf = {

    val x1 = Array(10d)
    val x2 = Array(2d)
    (1L to 2000L * 50 * 50).foreach(_ => covFunc.df_dSf(x1, x2))

  }

  /**
   * Tests for df_dEll
   */
  @Test def test_1D_df_dEll = {

    assertEquals(0, covFunc.df_dEll(Array(3d), Array(3d)), 0.0001)
    assertEquals(0.3441, covFunc.df_dEll(Array(2d), Array(5d)), 0.0001)
    assertEquals(1.8589, covFunc.df_dEll(Array(2d), Array(10d)), 0.0001)
    assertEquals(2.9264, new CovSEiso(sf = log(2d), log(200d)).df_dEll(Array(2d), Array(300d)), 0.0001)
  }

  @Test def multi_dim_df_dEll = {
    val rand = new Random(4656)
    val n = 2000
    val x1 = Array.fill(n)(rand.nextDouble)
    val x2 = Array.fill(n)(rand.nextDouble)

    val covValue = covFunc.df_dEll(x1, x2)
    assertEquals(2.4590, covValue, 0.0001)
  }

  @Test def perf_test_1d_df_dEll = {

    val x1 = Array(10d)
    val x2 = Array(2d)
    (1L to 200L * 50 * 50).foreach(_ => covFunc.df_dEll(x1, x2))

  }

  @Test def perf_test_1d_df_dEll_matrix_input = {

    val x = DenseMatrix.zeros[Double](1000, 1).toArray
    (1 to 10).foreach(_ => covFunc.df_dEll(x))
  }

  /**
   * Perf test for cov(Double,Double)
   */

  @Test def perf_cov_Double_Double = {
    val x1 = 10
    val x2 = 200

    for (i <- 1 to 4000000) covFunc.cov(x1, x2)
  }
}