import os, math, time

width = 50
height = 100

background = "."
fill = "="

screenOut = [[background * height] * width]

FOV = 60
camX, camY, camZ = 0, 0, 30
xRotation, yRotation, zRotation = 0, 0, 0

def reset():
    global screenOut
    screenOut = [[background * height] * width]

def pixel(x, y):
    x, y = round((x) + (height / 2)), round((y) + (width / 2))
    if x < height and y < width:
        if x > -1 and y > -1:
            screenOut[0][y] = screenOut[0][y][:x] + fill + screenOut[0][y][x+1:]

def display():
    for row in screenOut:
        for item in row:
            print(item)

def line(startX, startY, endX, endY):
    if endX - startX == 0:
        for y in range(min(startY, endY + 1), max(startY, endY + 1)):
            pixel(endX, y)
    
    elif endY - startY == 0:
        for x in range(min(startX, endX + 1), max(startX, endX + 1)):
            pixel(x, endY)

    else:
        slope = (endY - startY) / (endX - startX)
        b = (startX * (slope)) - startY
        if abs(endY - startY) <= abs(endX - startX):
            for x in range(min(startX, endX + 1), max(startX, endX + 1)):
                    pixel(x, slope * x - b)
        else:
            for y in range(min(startY, endY + 1), max(startY, endY + 1)):
                 pixel((y + b) / slope, y)

def threeDimensionalLine(startX, startY, startZ, endX, endY, endZ):
    cosz = math.cos(zRotation)
    sinz = math.sin(zRotation)
    cosx = math.cos(xRotation)
    sinx = math.sin(xRotation)
    cosy = math.cos(yRotation)
    siny = math.sin(yRotation)
   
    x1 = startX * cosz - startY * sinz
    y1 = startX * sinz + startY * cosz
    y1 = startY * cosx - startZ * sinx
    z1 = startY * sinx + startZ * cosx
    x1 = startX * cosy + startZ * siny
    z1 = -1 * startX * siny + startZ * cosy
    x2 = endX * cosz - endY * sinz
    y2 = endX * sinz + endY * cosz
    y2 = endY * cosx - endZ * sinx
    z2 = endY * sinx + endZ * cosx
    x2 = endX * cosy + endZ * siny
    z2 = -1 * endX * siny + endZ * cosy

    newStartX = FOV * ((x1 + camX) / (z1 + camZ))
    newStartY = (FOV / 2) * ((y1 + camY) / (z1 + camZ))
    newEndX = FOV * ((x2 + camX) / (z2 + camZ))
    newEndY = (FOV / 2) * ((y2 + camY) / (z2 + camZ))

    line(-round(newStartX), -round(newStartY), -round(newEndX), -round(newEndY))

def cube(x, y, z, size):
    threeDimensionalLine(x - size, y - size, z + size, x - size, y + size, z + size)
    threeDimensionalLine(x - size, y + size, z + size, x + size, y + size, z + size)
    threeDimensionalLine(x + size, y + size, z + size, x + size, y - size, z + size)
    threeDimensionalLine(x + size, y - size, z + size, x - size, y - size, z + size)
    threeDimensionalLine(x - size, y - size, z - size, x - size, y + size, z - size)
    threeDimensionalLine(x - size, y + size, z - size, x + size, y + size, z - size)
    threeDimensionalLine(x + size, y + size, z - size, x + size, y - size, z - size)
    threeDimensionalLine(x + size, y - size, z - size, x - size, y - size, z - size)
    threeDimensionalLine(x + size, y - size, z - size, x + size, y + size, z - size)
    threeDimensionalLine(x + size, y + size, z - size, x + size, y + size, z + size)
    threeDimensionalLine(x + size, y + size, z + size, x + size, y - size, z + size)
    threeDimensionalLine(x + size, y - size, z + size, x + size, y - size, z - size)
    threeDimensionalLine(x - size, y - size, z - size, x - size, y + size, z - size)
    threeDimensionalLine(x - size, y + size, z - size, x - size, y + size, z + size)
    threeDimensionalLine(x - size, y + size, z + size, x - size, y - size, z + size)
    threeDimensionalLine(x - size, y - size, z + size, x - size, y - size, z - size)
    threeDimensionalLine(x - size, y - size, z + size, x + size, y - size, z + size)
    threeDimensionalLine(x + size, y - size, z + size, x + size, y - size, z - size)
    threeDimensionalLine(x + size, y - size, z - size, x - size, y - size, z - size)
    threeDimensionalLine(x - size, y - size, z - size, x - size, y - size, z + size)
    threeDimensionalLine(x - size, y + size, z + size, x + size, y + size, z + size)
    threeDimensionalLine(x + size, y + size, z + size, x + size, y + size, z - size)
    threeDimensionalLine(x + size, y + size, z - size, x - size, y + size, z - size)
    threeDimensionalLine(x - size, y + size, z - size, x - size, y + size, z + size)

def pyramid(x, y, z, size):
    threeDimensionalLine(x - size, y - size, z - size, x + size, y - size, z - size)
    threeDimensionalLine(x + size, y - size, z - size, x + size, y - size, z + size)
    threeDimensionalLine(x + size, y - size, z + size, x - size, y - size, z + size)
    threeDimensionalLine(x - size, y - size, z + size, x - size, y - size, z - size)

    threeDimensionalLine(x - size, y - size, z - size, x, y + size, z)
    threeDimensionalLine(x + size, y - size, z - size, x, y + size, z)
    threeDimensionalLine(x - size, y - size, z + size, x, y + size, z)
    threeDimensionalLine(x + size, y - size, z + size, x, y + size, z)

while True:
    os.system('cls')
    display()
    reset()
    cube(0, 0, 0, 10)
    yRotation += 0.008