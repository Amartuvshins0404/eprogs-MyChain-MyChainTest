#!/bin/bash

echo "=========================================="
echo "    Data Structures & Algorithms Tests"
echo "=========================================="

# Test data structures
echo "Testing Data Structures..."
echo "-------------------------"

# Test ArrayLinearList
echo "✓ Testing ArrayLinearList..."
javac dataStructures/ArrayLinearList.java 2>/dev/null
if [ $? -eq 0 ]; then
    echo "  - Compilation: SUCCESS"
    java dataStructures.ArrayLinearList 2>/dev/null | head -5
else
    echo "  - Compilation: FAILED"
fi

# Test Chain
echo "✓ Testing Chain..."
javac dataStructures/Chain.java 2>/dev/null
if [ $? -eq 0 ]; then
    echo "  - Compilation: SUCCESS"
    java dataStructures.Chain 2>/dev/null | head -5
else
    echo "  - Compilation: FAILED"
fi

# Test MyChain
echo "✓ Testing MyChain..."
javac dataStructures/MyChain.java 2>/dev/null
if [ $? -eq 0 ]; then
    echo "  - Compilation: SUCCESS"
    java dataStructures.MyChain 2>/dev/null | head -5
else
    echo "  - Compilation: FAILED"
fi

echo "=========================================="
echo "           Tests Completed!"
echo "=========================================="
