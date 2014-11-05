package volume;

/**
 * This is a 2D Difference of Gaussian convolution kernel.
 * Retinal optimal relationship between the sigmas of the two gaussians is 1.6
 *
 * DoG2D(x) = 1 / Math.sqrt(2 Math.PI * sigma^2) e^(-(x^2+y^2) / 2 sigma^2) - 1 / Math.sqrt(2 Math.PI * (1.6 sigma)^2) e^(-(x^2+y^2) / 2 (1.6 sigma)^2)
 *
 * @author: Michael Abramoff, (c) 1999-2003 Michael Abramoff. All rights reserved.
 *
 * Small print:
 * Permission to use, copy, modify and distribute this version of this software or any parts
 * of it and its documentation or any parts of it ("the software"), for any purpose is
 * hereby granted, provided that the above copyright notice and this permission notice
 * appear intact in all copies of the software and that you do not sell the software,
 * or include the software in a commercial package.
 * The release of this software into the public domain does not imply any obligation
 * on the part of the author to release future versions into the public domain.
 * The author is free to make upgraded or improved versions of the software available
 * for a fee or commercially only.
 * Commercial licensing of the software is available by contacting the author.
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS, IMPLIED OR OTHERWISE, INCLUDING WITHOUT LIMITATION, ANY
 * WARRANTY OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.
 */
public class DoG2D extends Gaussian2D
{
        public static final double SIGMA_RELATIVE = 1.6;
        public DoG2D(double sigma)
        {
                this.sigma = sigma;
                if (sigma != 0)
                {
                        int width = (int) (6 * (sigma*SIGMA_RELATIVE) + 1);
                        if (width % 2 == 0) width++;
                        halfwidth = width / 2;
                        k = new double[halfwidth*2+1][halfwidth*2+1];
                        for (int m = -halfwidth; m <= halfwidth; m++)
                        for (int l = -halfwidth; l <= halfwidth; l++)
                                k[m+halfwidth][l+halfwidth] = function(l, m);
                }
                else
                        halfwidth = 0;
        }
        /**
        * Compute 2D Difference of Gaussian function (DoG) at x, y.
        * @param x the x position
        * @param y the y position.
        * @return a double with the value of the 2D Difference of Gaussian at x, y.
        */
        protected double function(double x, double y)
        {
                return 1.0 / (2 * Math.PI * Math.pow(sigma, 2)) * Math.exp(-(x*x + y*y) / (2 * Math.pow(sigma, 2)))
                        - 1.0 / (2 * Math.PI * Math.pow(sigma * SIGMA_RELATIVE, 2)) * Math.exp(-(x*x + y*y) / (2 * Math.pow(sigma * SIGMA_RELATIVE, 2)));
        }
        public String toString() { return "DoG 2D "+sigma;  }
}