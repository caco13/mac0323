/******************************************************************************
 *  Compilation:  javac BulgingSquares.java
 *  Execution:    java BulgingSquares
 *  Dependencies: StdDraw.java, java.awt.Color
 *
 *  Program draws an optical illusion from Akiyoshi Kitaoka. The center appears 
 *  to bulge outwards even though all squares are the same size. 
 *
 *  meu_prompt > java BulgingSquares
 *
 *  Exercise 14 http://introcs.cs.princeton.edu/java/15inout/
 * 
 ******************************************************************************/

// Standard draw. This class provides a basic capability for creating
// drawings with your programs. It uses a simple graphics model that
// allows you to create drawings consisting of points, lines, and
// curves in a window on your computer and to save the drawings to a
// file.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
import edu.princeton.cs.algs4.StdDraw; // StdDraw.setXscale, StdDraw.setYscale, ...

import java.awt.Color; // StdDraw.WHITE, StdDraw.BLACK

public class BulgingSquares {
    private static final double XMIN   = -75;
    private static final double XMAX   =  75;
    private static final double YMIN   = -75;
    private static final double YMAX   =  75;
    private static final double MARGIN =   2;
    private static final double RADIUS_MAX =   5;
    private static final double DIAM_MAX   = 2*RADIUS_MAX;
    private static final double RADIUS_MIN = 1.5;
    private static final double DIAM_MIN   = 2*RADIUS_MIN;
    private static final int MAX_SAME_TYPE_SQUARES = 6;
    

    /** 
     * Desenha um quadradinho no canto superior esquerdo de um quadrado com centro
     * no ponto (x, y) e lado DIAM_MAX. O quadradinho considera uma margem MARGIN
     * e tem lado DIAM_MIN.
     */
    private static void quadradoSuperiorEsquerdo(double x, double y, int color) {
        if (color == 0) {
            StdDraw.setPenColor(StdDraw.BLACK);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
        }
        StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
    }
    
    /** 
     * Desenha um quadradinho no canto inferior direito de um quadrado com centro
     * no ponto (x, y) e lado DIAM_MAX. O quadradinho considera uma margem MARGIN
     * e tem lado DIAM_MIN.
     */
    private static void quadradoInferiorDireito(double x, double y, int color) {
        if (color == 0) {
            StdDraw.setPenColor(StdDraw.BLACK);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
        }
        StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
    }

    /** 
     * Desenha um quadradinho no canto superior direito de um quadrado com centro
     * no ponto (x, y) e lado DIAM_MAX. O quadradinho considera uma margem MARGIN
     * e tem lado DIAM_MIN.
     */
    private static void quadradoSuperiorDireito(double x, double y, int color) {
        if (color == 0) {
            StdDraw.setPenColor(StdDraw.BLACK);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
        }
        StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
    }
    
    /** 
     * Desenha um quadradinho no canto inferior direito de um quadrado com centro
     * no ponto (x, y) e lado DIAM_MAX. O quadradinho considera uma margem MARGIN
     * e tem lado DIAM_MIN.
     */
    private static void quadradoInferiorEsquerdo(double x, double y, int color) {
        if (color == 0) {
            StdDraw.setPenColor(StdDraw.BLACK);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
        }
        StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
    }
    
    
    public static void main(String[] args) {
        // set the scale of the coordinate system
        StdDraw.setXscale(XMIN-MARGIN, XMAX+MARGIN);
        StdDraw.setYscale(YMIN-MARGIN, YMAX+MARGIN);
        StdDraw.enableDoubleBuffering();
        
        // clear the background
        StdDraw.clear(StdDraw.WHITE);

        // quadrados pretos, linhas ímpares
        for (double y = YMAX - 2*MARGIN; y > YMIN; y -= 2*DIAM_MAX) {
            for (double x = XMIN + 2*MARGIN; x < XMAX; x += 2*DIAM_MAX) {
                StdDraw.filledSquare(x, y, RADIUS_MAX);
            }
        }
        
        // quadrados pretos, linhas pares
        for (double y = YMAX - DIAM_MAX - 2*MARGIN; y > YMIN + DIAM_MAX; y -= 2*DIAM_MAX) {
            for (double x = XMIN + DIAM_MAX + 2*MARGIN; x < XMAX - DIAM_MAX; x += 2*DIAM_MAX) {
                StdDraw.filledSquare(x, y, RADIUS_MAX);
            }
        }
        
        /**
         * Quadradinhos nas diagonais secundárias e principais dos quadrados do
         * tabuleiro, exceto as mais externas.
         */
        // coordenadas do centro do tabuleiro
        double xCentral = 2*MARGIN - RADIUS_MAX;
        double yCentral = 3*MARGIN - RADIUS_MAX;
        
        for (int i = 1; i <= MAX_SAME_TYPE_SQUARES; i++) {
            int color = i % 2;
            for (int num = 1; num <= i; num++) {
                // quadradinhos na diagonal principal
                quadradoSuperiorEsquerdo(xCentral + i*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral + num*DIAM_MAX, color);
                quadradoInferiorDireito(xCentral + i*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral + num*DIAM_MAX, color);
                quadradoSuperiorEsquerdo(xCentral - i*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral - num*DIAM_MAX, color);
                quadradoInferiorDireito(xCentral - i*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral - num*DIAM_MAX, color);
                
                // quadradinhos na diagonal secundária
                quadradoSuperiorDireito(xCentral - i*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral + num*DIAM_MAX, color);
                quadradoInferiorEsquerdo(xCentral - i*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral + num*DIAM_MAX, color);
                quadradoSuperiorDireito(xCentral + i*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral - num*DIAM_MAX, color);
                quadradoInferiorEsquerdo(xCentral + i*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral - num*DIAM_MAX, color);
            }
        }
        
        /**
         * Quadradinhos nas diagonais secundárias e principais dos quadrados mais externos
         * com quadradinhos em seu interior.
         */
        for (int num = 1; num <= MAX_SAME_TYPE_SQUARES / 2; num++) {
            // quadradinhos na diagonal principal
            quadradoSuperiorEsquerdo(xCentral + 5*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral + 2*DIAM_MAX + num*DIAM_MAX, 1);
            quadradoInferiorDireito(xCentral + 5*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral + 2*DIAM_MAX + num*DIAM_MAX, 1);
            quadradoSuperiorEsquerdo(xCentral - 5*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral - 2*DIAM_MAX - num*DIAM_MAX, 1);
            quadradoInferiorDireito(xCentral - 5*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral - 2*DIAM_MAX - num*DIAM_MAX, 1);
            
            // quadradinhos na diagonal secundária
            quadradoSuperiorDireito(xCentral - 5*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral + 2*DIAM_MAX + num*DIAM_MAX, 1);
            quadradoInferiorEsquerdo(xCentral - 5*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral + 2*DIAM_MAX + num*DIAM_MAX, 1);
            quadradoSuperiorDireito(xCentral + 5*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral - 2*DIAM_MAX - num*DIAM_MAX, 1);
            quadradoInferiorEsquerdo(xCentral + 5*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral - 2*DIAM_MAX - num*DIAM_MAX, 1);
            
            
            
        }
        
        /*
         * Eixos vertical e horiontal
         */
        for (int i = 0; i < MAX_SAME_TYPE_SQUARES; i++) {
            int color = i % 2;
            // quadradinhos na borda superior
            quadradoSuperiorEsquerdo(xCentral, yCentral - (i+1)*DIAM_MAX, color);
            quadradoSuperiorDireito(xCentral, yCentral - (i+1)*DIAM_MAX, color);
            
            // quadradinhos na borda inferior
            quadradoInferiorEsquerdo(xCentral, yCentral + (i+1)*DIAM_MAX, color);
            quadradoInferiorDireito(xCentral, yCentral + (i+1)*DIAM_MAX, color);
            
            // quadradinhos na borda direita
            quadradoSuperiorDireito(xCentral - (i+1)*DIAM_MAX, yCentral, color);
            quadradoInferiorDireito(xCentral - (i+1)*DIAM_MAX, yCentral, color);
            
            // quadradinhos na borda esquerda
            quadradoSuperiorEsquerdo(xCentral + (i+1)*DIAM_MAX, yCentral, color);
            quadradoInferiorEsquerdo(xCentral + (i+1)*DIAM_MAX, yCentral, color);
            
        }
            
        
        // copy offscreen buffer to onscreen
        StdDraw.show();
    }

} 
