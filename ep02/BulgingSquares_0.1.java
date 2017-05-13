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
    // constantes... vixe! use se desejar
    private static final double XMIN   = -75;
    private static final double XMAX   =  75;
    private static final double YMIN   = -75;
    private static final double YMAX   =  75;
    private static final double MARGIN =   2;
    private static final double RADIUS_MAX =   5;
    private static final double DIAM_MAX   = 2*RADIUS_MAX;
    private static final double RADIUS_MIN = 1.5;
    private static final double DIAM_MIN   = 2*RADIUS_MIN;
    
    // Desenha os dois quadradinhos na diagonal secundária de uma célula do tabuleiro
    private static void doisQuadradosDiagonalPrincipal(double x, double y, int color) {
        if (color == 0) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        }
    }
    
    // Desenha os dois quadradinhos na diagonal principal de uma célula do tabuleiro
    private static void doisQuadradosDiagonalSecundaria(double x, double y, int color) {
        if (color == 0) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        }
    }
    
    // Desenha os dois quadradinhos colados na parte inferior da célula
    private static void doisQuadradosBordaInferior(double x, double y, int color) {
        if (color == 1) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        }
    }
    
    // Desenha os dois quadradinhos colados na parte superior da célula
    private static void doisQuadradosBordaSuperior(double x, double y, int color) {
        if (color == 1) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
        }
    }
    
    // Desenha os dois quadradinhos colados na borda direita da célula
    private static void doisQuadradosBordaDireita(double x, double y, int color) {
        if (color == 1) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x + RADIUS_MAX - MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        }
    }
    
    // Desenha os dois quadradinhos colados na borda esquerda da célula
    private static void doisQuadradosBordaEsquerda(double x, double y, int color) {
        if (color == 1) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y + RADIUS_MAX - MARGIN, RADIUS_MIN);
            StdDraw.filledSquare(x - RADIUS_MAX + MARGIN, y - RADIUS_MAX + MARGIN, RADIUS_MIN);
        }
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
        
        // Diagonais secundárias e principais, exceto as mais externas
        double xCentral = 2*MARGIN - RADIUS_MAX;
        double yCentral = 3*MARGIN - RADIUS_MAX;
        for (int i = 1; i <= 6; i++) {
            int color = i % 2;
            for (int num = 1; num <= i; num++) {
                doisQuadradosDiagonalPrincipal(xCentral + i*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral + num*DIAM_MAX, color);
                doisQuadradosDiagonalPrincipal(xCentral - i*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral - num*DIAM_MAX, color);
                doisQuadradosDiagonalSecundaria(xCentral - i*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral + num*DIAM_MAX, color);
                doisQuadradosDiagonalSecundaria(xCentral + i*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral - num*DIAM_MAX, color);
            }
        }
        
        // Diagonais secundárias e principais mais externas
        for (int num = 1; num <= 3; num++) {
            doisQuadradosDiagonalPrincipal(xCentral + 5*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral + 2*DIAM_MAX + num*DIAM_MAX, 1);
            doisQuadradosDiagonalPrincipal(xCentral - 5*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral - 2*DIAM_MAX - num*DIAM_MAX, 1);
            doisQuadradosDiagonalSecundaria(xCentral - 5*DIAM_MAX + (num - 1)*DIAM_MAX, yCentral + 2*DIAM_MAX + num*DIAM_MAX, 1);
            doisQuadradosDiagonalSecundaria(xCentral + 5*DIAM_MAX - (num - 1)*DIAM_MAX, yCentral - 2*DIAM_MAX - num*DIAM_MAX, 1);
        }
        
        // Eixo vertical central
        for (int i = 1; i <= 6; i++) {
            int color = i % 2;
            doisQuadradosBordaSuperior(xCentral, yCentral - i*DIAM_MAX, color);
            doisQuadradosBordaInferior(xCentral, yCentral + i*DIAM_MAX, color);
            doisQuadradosBordaDireita(xCentral - i*DIAM_MAX, yCentral, color);
            doisQuadradosBordaEsquerda(xCentral + i*DIAM_MAX, yCentral, color);
        }
            
        
        // copy offscreen buffer to onscreen
        StdDraw.show();
    }

} 
