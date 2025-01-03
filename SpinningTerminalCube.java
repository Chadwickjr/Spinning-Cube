public class SpinningTerminalCube {

    static int width = 100, height = 50, FOV = 50;
    static String background = "-", fill = "#";
    static String[][] screenOut = new String[height][width];
    static double xRotation = 0, yRotation = 0, zRotation = 0;

    static int round(double num) {
        return (int) (num + 0.5);
    }

    static void resetScreen() {
        for(int row = 0; row < height; row++) {
            for(int point = 0; point < width; point++) {
                screenOut[row][point] = background;
            }
        }
    }

    static void wait(int ms) {
    try {
        Thread.sleep(ms);
    }
    catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
    }
}

    static void displayScreen() {
        wait(50);
        System.out.print("\033[2J\033[1;1H");
        for(int row = 0; row < height; row++) {
            String newOut = "";
            for(String s : screenOut[row]) {
                newOut = newOut + s;
            }
            System.out.println(newOut);
        }
    }

    static void point(double x, double y) {
        int newX = (int) (x + 0.5) + (int) (width / 2);
        int newY = (int) (-(y + 0.5) / 2) + (int) (height / 2);
        if(newX < width && newY < height) {
            screenOut[newY][newX] = fill;
        }
    }

    static void line(double x1, double y1, double x2, double y2) {
        int startX = round(x1);
        int startY = round(y1);
        int endX = round(x2);
        int endY = round(y2);
        if(endX - startX == 0) {
            for(int y = Math.min(startY, endY + 1); y < Math.max(startY, endY + 1); y++) {
                point(endX, y);
            }
        }
        else if(endY - startY == 0) {
            for(int x = Math.min(startX, endX + 1); x < Math.max(startX, endX + 1); x++) {
                point(x, endY);
            }
        }
        else {
            double slope = (double) (endY - startY) / (endX - startX);
            double b = (startX * slope) - startY;
            if(Math.abs(endY - startY) <= Math.abs(endX - startX)) {
                for(int x = Math.min(startX, endX + 1); x < Math.max(startX, endX + 1); x++) {
                    point(x, slope * x - b);
                }
            }
            else {
                for(int y = Math.min(startY, endY + 1); y < Math.max(startY, endY + 1); y++) {
                    point((y + b) / slope, y);
                }
            }
        }
    }

    static void threeDimensionalLine(int startX, int startY, int startZ, int endX, int endY, int endZ) {
        double cosz = Math.cos(zRotation);
        double sinz = Math.sin(zRotation);
        double cosx = Math.cos(xRotation);
        double sinx = Math.sin(xRotation);
        double cosy = Math.cos(yRotation);
        double siny = Math.sin(yRotation);

        double x1, x2, y1, y2, z1, z2;
       
        x1 = startX * cosz - startY * sinz;
        y1 = startX * sinz + startY * cosz;
        y1 = startY * cosx - startZ * sinx;
        z1 = startY * sinx + startZ * cosx;
        x1 = startX * cosy + startZ * siny;
        z1 = -1 * startX * siny + startZ * cosy;
        x2 = endX * cosz - endY * sinz;
        y2 = endX * sinz + endY * cosz;
        y2 = endY * cosx - endZ * sinx;
        z2 = endY * sinx + endZ * cosx;
        x2 = endX * cosy + endZ * siny;
        z2 = -1 * endX * siny + endZ * cosy;
    
        double newStartX = FOV * ((x1) / (z1 + 30));
        double newStartY = FOV * ((y1) / (z1 + 30));
        double newEndX = FOV * ((x2) / (z2 + 30));
        double newEndY = FOV * ((y2) / (z2 + 30));
    
        line(newStartX, newStartY, newEndX, newEndY);
    }

    static void cube(int x, int y, int z, int size) {
        threeDimensionalLine(x - size, y - size, z + size, x - size, y + size, z + size);
        threeDimensionalLine(x - size, y + size, z + size, x + size, y + size, z + size);
        threeDimensionalLine(x + size, y + size, z + size, x + size, y - size, z + size);
        threeDimensionalLine(x + size, y - size, z + size, x - size, y - size, z + size);
        threeDimensionalLine(x - size, y - size, z - size, x - size, y + size, z - size);
        threeDimensionalLine(x - size, y + size, z - size, x + size, y + size, z - size);
        threeDimensionalLine(x + size, y + size, z - size, x + size, y - size, z - size);
        threeDimensionalLine(x + size, y - size, z - size, x - size, y - size, z - size);
        threeDimensionalLine(x + size, y - size, z - size, x + size, y + size, z - size);
        threeDimensionalLine(x + size, y + size, z - size, x + size, y + size, z + size);
        threeDimensionalLine(x + size, y + size, z + size, x + size, y - size, z + size);
        threeDimensionalLine(x + size, y - size, z + size, x + size, y - size, z - size);
        threeDimensionalLine(x - size, y - size, z - size, x - size, y + size, z - size);
        threeDimensionalLine(x - size, y + size, z - size, x - size, y + size, z + size);
        threeDimensionalLine(x - size, y + size, z + size, x - size, y - size, z + size);
        threeDimensionalLine(x - size, y - size, z + size, x - size, y - size, z - size);
        threeDimensionalLine(x - size, y - size, z + size, x + size, y - size, z + size);
        threeDimensionalLine(x + size, y - size, z + size, x + size, y - size, z - size);
        threeDimensionalLine(x + size, y - size, z - size, x - size, y - size, z - size);
        threeDimensionalLine(x - size, y - size, z - size, x - size, y - size, z + size);
        threeDimensionalLine(x - size, y + size, z + size, x + size, y + size, z + size);
        threeDimensionalLine(x + size, y + size, z + size, x + size, y + size, z - size);
        threeDimensionalLine(x + size, y + size, z - size, x - size, y + size, z - size);
        threeDimensionalLine(x - size, y + size, z - size, x - size, y + size, z + size);
    }

    static void pyramid(int x, int y, int z, int size) {
        threeDimensionalLine(x - size, y - size, z - size, x + size, y - size, z - size);
        threeDimensionalLine(x + size, y - size, z - size, x + size, y - size, z + size);
        threeDimensionalLine(x + size, y - size, z + size, x - size, y - size, z + size);
        threeDimensionalLine(x - size, y - size, z + size, x - size, y - size, z - size);
        threeDimensionalLine(x - size, y - size, z - size, x, y + size, z);
        threeDimensionalLine(x + size, y - size, z - size, x, y + size, z);
        threeDimensionalLine(x - size, y - size, z + size, x, y + size, z);
        threeDimensionalLine(x + size, y - size, z + size, x, y + size, z);
    }

    public static void main(String[] args) {
        while(true) {
            resetScreen();
            cube(0, 0, 0, 12);
            yRotation += 0.03;
            displayScreen();
        }
    }
}