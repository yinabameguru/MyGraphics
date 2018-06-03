package com.jza;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyGraphics extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    final static int width = 600;
    final static int height = 600;
    final static int x0 = 300;
    final static int y0 = 300;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.strokeLine(0, 300, 600, 300);
        gc.strokeLine(300, 0, 300, 600);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(0.5);
        Coordinate c1 = new Coordinate(0, 200);
        Coordinate c2 = new Coordinate(100, 250);
        Coordinate c3 = new Coordinate(100, 150);
        Coordinate c4 = new Coordinate(0, 0);
        Coordinate c5 = new Coordinate(0, 51);
        Coordinate c6 = new Coordinate(51, 51);
        Coordinate c7 = new Coordinate(51, 0);
        Coordinate c8 = new Coordinate(100, 0);
        Coordinate c9 = new Coordinate(100, 51);
        Coordinate c10 = new Coordinate(151, 51);
        Coordinate c11 = new Coordinate(151, 0);
        List<Coordinate> coordinates = new LinkedList<Coordinate>();

        BresenhamLine line1 = new BresenhamLine();
        coordinates = line1.drawLine(coordinates, c1, c2);
        draw(gc,coordinates);

        int r = 100;
        com.jza.MyCircle circle = new com.jza.MyCircle();
        coordinates = circle.drawCircle(coordinates,r);
        draw(gc,coordinates);

        com.jza.EdgeMarkFill edgeMarkFill = new com.jza.EdgeMarkFill();
        coordinates = edgeMarkFill.edgeMarkFill(c8, c9, c10,c11);
        draw(gc,coordinates);

        EdgeMarkFill.SeedFill seedFill = new EdgeMarkFill.SeedFill();
        coordinates = seedFill.seedFill(coordinates, new Coordinate(5, 5), c4, c5, c6,c7);
        draw(gc,coordinates);

//        Clipping clipping = new Clipping();
//        coordinates = clipping.clipping(coordinates, 100, -50, -50, 100, new Coordinate(-100, -100), new Coordinate(100, 100));
//        draw(gc,coordinates);
//        WriteFIle writeFIle = new WriteFIle();
//        writeFIle.writeFile("src/com/jza/outPut.txt",coordinates.toString());
//        ReadFile readFile = new ReadFile();
//        coordinates = readFile.readFile("src/com/jza/outPut.txt");
//        draw(gc,coordinates);


        LineType lineType = new LineType();
        coordinates = lineType.DottedLine(coordinates, new Coordinate(-100, -100), new Coordinate(-20, -20), 10);
        draw(gc,coordinates);


        TwoDimensionalTransformation twoDimensionalTransformation = new TwoDimensionalTransformation();
        coordinates = twoDimensionalTransformation.panning(coordinates, new Coordinate(0, 0), new Coordinate(100, 100), -50, 50);
        draw(gc,coordinates);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void draw(GraphicsContext gc,List<Coordinate> coordinates) {
        int x = 0;
        int y = 0;
        for(Coordinate coordinate : coordinates){
            x = coordinate.getX() + x0;
            y = -coordinate.getY() + y0;
            gc.strokeLine(x,y,x+1,y+1);
        }
    }
}
class Coordinate{
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;
    Coordinate(int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "com.jza.Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

//class MyLine {
//    public void drawLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
//        if (x1 > x2) {
//            int temp = x1;
//            x1 = x2;
//            x2 = temp;
//            temp = y1;
//            y1 = y2;
//            y2 = temp;
//        }
//
//        int a = y1 - y2;
//        int b = x2 - x1;
//        int c = x1 * y2 - x2 * y1;
//        double d = a + b / 2;r
//        int right = a;
//        int rightTop = a + b;
//        com.jza.Taisite2.draw(gc, x1, y1);
//        while (x1 < x2) {
//            if (d > 0) {
//                x1 = x1 + 1;
//                d = d + right;
//                com.jza.Taisite2.draw(gc, x1, y1);
//            } else {
//                x1 = x1 + 1;
//                y1 = y1 + 1;
//                d = d + rightTop;
//                com.jza.Taisite2.draw(gc, x1, y1);
//            }
//        }
//    }
//}
class BresenhamLine {
    public List<Coordinate> drawLine(List<Coordinate> coordinates,Coordinate coordinate1, Coordinate coordinate2) {
        int x1 = coordinate1.getX();
        int y1 = coordinate1.getY();
        int x2 = coordinate2.getX();
        int y2 = coordinate2.getY();
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }else if (x1 == x2){
            if (y1 > y2){
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int i = y1; i < y2; i++) {
                coordinates.add(new Coordinate(x1,i));
            }
            return coordinates;
        }

        double k = (y2 - y1)/(x2 - x1 + 0.0);
        double e = -0.5;
        int x = x1;
        int y = y1;
        if(Math.abs(k) > 1){
            k = 1/k;
            for (int i = x1; i < y2; i++) {
                coordinates.add(new Coordinate(x,y));
                y = y + 1;
                e = e + k;
                if (e >= 0){
                    x = x + 1;
                    e = e - 1;
                }
            }
        }else {
            for (int i = x1; i <=x2 ; i++) {
                coordinates.add(new Coordinate(x,y));
                x = x + 1;
                e = e +k;
                if (e >= 0){
                    y = y+1;
                    e = e-1;
                }
            }
        }
        return coordinates;
    }
}
class MyCircle {
    public List<Coordinate> drawCircle(List<Coordinate> coordinates,int r){
        int x = 0;
        int y = r;
        double d = 1.25 - r;
        int right = 2*x + 3;
        int rightBottom = 2*x - 2*y +5;
        while (x <= y){
            if(d > 0){
                x++;
                y--;
//                d = d + rightBottom;
                d = d + 2*x - 2*y +5;
                coordinates.add(new Coordinate(x,y));
                coordinates.add(new Coordinate(x,-y));
                coordinates.add(new Coordinate(-x,-y));
                coordinates.add(new Coordinate(-x,y));
                coordinates.add(new Coordinate(y,x));
                coordinates.add(new Coordinate(y,-x));
                coordinates.add(new Coordinate(-y,-x));
                coordinates.add(new Coordinate(-y,x));
            }else {
                x++;
//                d = d + right;
                d = d + 2*x + 3;
                coordinates.add(new Coordinate(x,y));
                coordinates.add(new Coordinate(x,-y));
                coordinates.add(new Coordinate(-x,-y));
                coordinates.add(new Coordinate(-x,y));
                coordinates.add(new Coordinate(y,x));
                coordinates.add(new Coordinate(y,-x));
                coordinates.add(new Coordinate(-y,-x));
                coordinates.add(new Coordinate(-y,x));
            }
        }
        return coordinates;
    }
}
class EdgeMarkFill{
    public List<Coordinate> edgeMarkFill(Coordinate... coordinates){
        Coordinate start;
        Coordinate end;
        List<Coordinate> outs = new LinkedList<Coordinate>();
        List<Coordinate> marks = new LinkedList<Coordinate>();
        BresenhamLine bresenhamLine = new BresenhamLine();
        for (int i = 0; i < coordinates.length; i++) {
            if (i == coordinates.length - 1){
                start = coordinates[i];
                end = coordinates[0];
                bresenhamLine.drawLine(marks, start, end);
                break;
            }
            start = coordinates[i];
            end = coordinates[i + 1];
            bresenhamLine.drawLine(marks, start, end);

        }
        boolean inside = false;
        for (int y = 0; y < 300; y++) {
            for (int x = 0; x < 300; x++) {
                for (Coordinate coordinate : marks){
                    if (coordinate.getX() == x && coordinate.getY() == y){
                        inside = !inside;
                        break;
                    }
                }
                if (inside == true){
                    outs.add(new Coordinate(x,y));
                }
            }
        }
        return outs;
    }

    static class SeedFill{
        public List<Coordinate> seedFill(List<Coordinate> coordinates1,Coordinate seed,Coordinate... coordinates){
            Coordinate start;
            Coordinate end;
            List<Coordinate> outs = new LinkedList<Coordinate>();
            List<Coordinate> marks = new LinkedList<Coordinate>();
            BresenhamLine bresenhamLine = new BresenhamLine();
            for (int i = 0; i < coordinates.length; i++) {
                if (i == coordinates.length - 1){
                    start = coordinates[i];
                    end = coordinates[0];
                    bresenhamLine.drawLine(marks, start, end);
                    break;
                }
                start = coordinates[i];
                end = coordinates[i + 1];
                bresenhamLine.drawLine(marks, start, end);
            }
            Queue<Coordinate> queue = new LinkedList<>();
            queue.offer(seed);
            marks.add(seed);
            outs.add(seed);
            int xTop = 0;
            int yTop = 0;
            int xBottom = 0;
            int yBottom = 0;
            int xLeft = 0;
            int yLeft = 0;
            int xRight = 0;
            int yRight = 0;
            boolean flagTop = true;
            boolean flagBottom = true;
            boolean flagLift = true;
            boolean flagRight = true;

            while (!(queue.isEmpty())){
                Coordinate hand = queue.poll();
                xTop = hand.getX();
                yTop = hand.getY() + 1;
                xBottom = hand.getX();
                yBottom = hand.getY() - 1;
                xLeft = hand.getX() - 1;
                yLeft = hand.getY();
                xRight = hand.getX() + 1;
                yRight = hand.getY();
                flagTop = true;
                flagBottom = true;
                flagLift = true;
                flagRight = true;
                for (Coordinate coordinate : marks){
                    if (coordinate.getX() == xTop && coordinate.getY() == yTop){
                        flagTop = false;
                    }
                    else if (coordinate.getX() == xBottom && coordinate.getY() == yBottom){
                        flagBottom = false;
                    }
                    else if (coordinate.getX() == xLeft && coordinate.getY() == yLeft){
                        flagLift = false;
                    }
                    else if (coordinate.getX() == xRight && coordinate.getY() == yRight){
                        flagRight = false;
                    }
                }
                if (flagTop == true){
                    Coordinate top = new Coordinate(xTop, yTop);
                    queue.offer(top);
                    outs.add(top);
                    marks.add(top);
                }
                if (flagBottom == true){
                    Coordinate bottom = new Coordinate(xBottom, yBottom);
                    queue.offer(bottom);
                    outs.add(bottom);
                    marks.add(bottom);
                }
                if (flagLift == true){
                    Coordinate left = new Coordinate(xLeft, yLeft);
                    queue.offer(left);
                    outs.add(left);
                    marks.add(left);
                }
                if (flagRight == true){
                    Coordinate right = new Coordinate(xRight, yRight);
                    queue.offer(right);
                    outs.add(right);
                    marks.add(right);
                }
            }
            return outs;
        }
    }


//class com.jza.EdgeMarkFill{
//    public List<com.jza.Coordinate> edgeMarkFill(com.jza.Coordinate... coordinates){
//        com.jza.Coordinate start;
//        com.jza.Coordinate end;
//        List<com.jza.Coordinate> outs = new LinkedList<com.jza.Coordinate>();
//        List<com.jza.Coordinate> marks = new LinkedList<com.jza.Coordinate>();
//        com.jza.BresenhamLine bresenhamLine = new com.jza.BresenhamLine();
//        for (int i = 0; i < coordinates.length; i++) {
//            if (i == coordinates.length - 1){
//                start = coordinates[i];
//                end = coordinates[0];
//                bresenhamLine.drawLine(marks, start, end);
//                break;
//            }
//            start = coordinates[i];
//            end = coordinates[i + 1];
//            bresenhamLine.drawLine(marks, start, end);
//
//        }
//        boolean inside = false;
//        boolean now = false;
//        com.jza.Coordinate pro = new com.jza.Coordinate(Integer.MAX_VALUE, Integer.MAX_VALUE);
//        for (int y = 0; y < 300; y++) {
//            for (int x = 0; x < 300; x++) {
//                now = false;
//                for (com.jza.Coordinate coordinate : marks){
//                    if (coordinate.getX() == x && coordinate.getY() == y){
//                        now = true;
//                        inside = !inside;
//                        for (com.jza.Coordinate coordinate2 : marks){
//                            if (coordinate2.getX() == pro.getX() && coordinate2.getY() == pro.getY()){
//                                inside = !inside;
//                                break;
//                            }
//                        }
//                        break;
//                    }
//                }
//                if (now == false){
//                    for (com.jza.Coordinate coordinate2 : marks){
//                        if (coordinate2.getX() == pro.getX() && coordinate2.getY() == pro.getY()){
//                            inside = !inside;
//                            break;
//                        }
//                    }
//                }
//
//
////                if (marks.contains(now)){
////                    if (!(marks.contains(pro)))
////                        inside = !inside;
////                }
//                pro = new com.jza.Coordinate(x,y);
//
//                    if (inside == true){
//                        outs.add(new com.jza.Coordinate(x,y));
//                        System.out.println(x + "   " + y);
//                    }
//            }
////            System.out.println();
//        }
//        return outs;
//    }
    //                for (com.jza.Coordinate coordinate : marks){
//                    if (coordinate.getX() == x && coordinate.getY() == y){
//                        if ()
//                        inside = !inside;
//                        break;
//                    }
//                }
}

class LineType{
    public List<Coordinate> DottedLine(List<Coordinate> coordinates, Coordinate start, Coordinate end, int width){
        BresenhamLine bresenhamLine = new BresenhamLine();
        coordinates = bresenhamLine.drawLine(coordinates, start, end);
        int[] flag = {1,1,1,1,1,0,0,0,0,0};
        Queue<Coordinate> queue = (Queue<Coordinate>)coordinates;
        Queue<Coordinate> queue2 = new LinkedList<>();
        int i = 0;
        while (!queue.isEmpty()){
            Coordinate hand = queue.poll();
            if (flag[i % flag.length] == 1){
                for (int j = hand.getX() - width/2; j < hand.getX() + width/2; j++) {
                    queue2.offer(new Coordinate(j,hand.getY()));
                }
            }
            i++;
        }
        return (List<Coordinate>) queue2;
    }
//    public List<Coordinate> WaveLine(List<Coordinate> coordinates, Coordinate start, Coordinate end, int width){
//
//    }

}
class Clipping{
    public List<Coordinate> clipping(List<Coordinate> coordinates,int top, int bottom, int left, int right, Coordinate start, Coordinate end){
        int startArea = 0b0000;
        int endArea = 0b0000;
        int leftFlag = 0b0001;
        int rightFlag = 0b0010;
        int bottomFlag = 0b0100;
        int topFlag = 0b1000;
        boolean startStatus = false;
        boolean endStatus = false;
        //y = kx +b
        double k = (end.getY() - start.getY())/(end.getX() - start.getX());
        double b = start.getY() - k * start.getX();
        if (start.getX() < left) {
            startArea = startArea | leftFlag;
        } else if (start.getX() > right) {
            startArea = startArea | rightFlag;
        }
        if (start.getY() < bottom) {
            startArea = startArea | bottomFlag;
        } else if (start.getY() > top) {
            startArea = startArea | topFlag;
        }
        if (end.getX() < left) {
            endArea = endArea | leftFlag;
        } else if (end.getX() > right) {
            endArea = endArea | rightFlag;
        }
        if (end.getY() < bottom) {
            endArea = endArea | bottomFlag;
        } else if (end.getY() > top) {
            endArea = endArea | topFlag;
        }
        while (true) {
            if (startArea == 0 && endArea == 0) {
                BresenhamLine bresenhamLine = new BresenhamLine();
                coordinates = bresenhamLine.drawLine(coordinates, start, end);
                return coordinates;
            } else if (!((startArea & endArea) == 0)) {
                coordinates.clear();
                return coordinates;
            } else {
                if (!startStatus){
                    if (!((startArea & leftFlag) == 0)) {
                        int x = left;
                        int y = (int) (k * x + b);
                        start = new Coordinate(x, y);
                        startStatus = true;
                        startArea = 0b0000;
                    } else if (!((startArea & rightFlag) == 0)) {
                        int x = right;
                        int y = (int) (k * x + b);
                        start = new Coordinate(x, y);
                        startStatus = true;
                        startArea = 0b0000;
                    } else if (!((startArea & bottomFlag) == 0)) {
                        int x = bottom;
                        int y = (int) (k * x + b);
                        start = new Coordinate(x, y);
                        startStatus = true;
                        startArea = 0b0000;
                    } else if (!((startArea & topFlag) == 0)) {
                        int x = top;
                        int y = (int) (k * x + b);
                        start = new Coordinate(x, y);
                        startStatus = true;
                        startArea = 0b0000;
                    }
                } else {
                    if (!((endArea & leftFlag) == 0)) {
                        int x = left;
                        int y = (int) (k * x + b);
                        end = new Coordinate(x, y);
                        endStatus = true;
                        endArea = 0b0000;
                    } else if (!((endArea & rightFlag) == 0)) {
                        int x = right;
                        int y = (int) (k * x + b);
                        end = new Coordinate(x, y);
                        endStatus = true;
                        endArea = 0b0000;
                    } else if (!((endArea & bottomFlag) == 0)) {
                        int x = bottom;
                        int y = (int) (k * x + b);
                        end = new Coordinate(x, y);
                        endStatus = true;
                        endArea = 0b0000;
                    } else if (!((endArea & topFlag) == 0)) {
                        int x = top;
                        int y = (int) (k * x + b);
                        end = new Coordinate(x, y);
                        endStatus = true;
                        endArea = 0b0000;
                    }
                }
            }
        }
    }
}
class WriteFIle{
    public void writeFile(String file, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ReadFile {
    public LinkedList<Coordinate> readFile(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        LinkedList<Integer> x = new LinkedList<>();
        LinkedList<Integer> y = new LinkedList<>();
        int tempchar;
        String tempNum = "";
        LinkedList<Coordinate> coordinates = new LinkedList<>();
        try {
            reader = new InputStreamReader(new FileInputStream(file));

            while ((tempchar = reader.read()) != -1) {
                char c = ((char) tempchar);
                tempNum = "";
                if (((char) tempchar) == '=') {
                    while ((tempchar = reader.read()) != -1 && ((char) tempchar) != ',' && ((char) tempchar) != '}') {
                        char c2 = ((char) tempchar);
                        tempNum += (char) tempchar;
                    }
                    if (!"".equals(tempNum)){
                        if (((char) tempchar) == ','){
                            x.add(Integer.parseInt(tempNum));
                        }else {
                            y.add(Integer.parseInt(tempNum));
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < x.size(); i++) {
            coordinates.add(new Coordinate(x.get(i),y.get(i)));
        }
        return coordinates;


    }
}
class TwoDimensionalTransformation{
    public List<Coordinate> panning(List<Coordinate> coordinates, Coordinate start, Coordinate end, int dx, int dy ){
        BresenhamLine bresenhamLine = new BresenhamLine();
        coordinates = bresenhamLine.drawLine(coordinates, start, end);
        int[][] a = {{1,0,0},{0,1,0},{dx,dy,1}};
        int[] b = new int[3];
        int[][] c = new int[coordinates.size()][a.length];
        int m = 0;
        int temp = 0;
        for(Coordinate coordinate : coordinates){
            b[0] = coordinate.getX();
            b[1] = coordinate.getY();
            b[2] = 1;
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    temp += a[j][i] * b[j];
                }
                c[m][i] = temp;
                temp = 0;
            }
            m++;
        }
        coordinates.clear();
        int x = 0;
        int y = 0;
        for (int j = 0; j < c.length; j++) {
            for (int k = 0; k < 2; k++) {
                if (k == 0)
                    x = c[j][k];
                else
                    y = c[j][k];
            }
            coordinates.add(new Coordinate(x,y));
        }
        return coordinates;
    }
}








