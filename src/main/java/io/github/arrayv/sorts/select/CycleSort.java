package io.github.arrayv.sorts.select;

import java.awt.Color;

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

/*
 *
MIT License

Copyright (c) 2021 aphitorite

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

public final class CycleSort extends Sort {
    public CycleSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("Cycle");
        this.setRunAllSortsName("Cycle Sort");
        this.setRunSortName("Cyclesort");
        this.setCategory("Selection Sorts");
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

	private int countLesser(int[] array, int a, int b, int t) {
		int r = a;

		for(int i = a+1; i < b; i++) {
			Highlights.markArray(1, r);
			Highlights.markArray(2, i);
			Delays.sleep(0.01);
			int cmp = Reads.compareIndexValue(array, i, t, 0.05, true) < 0 ? 1 : 0;
			if(cmp == 1) {
				Highlights.colorCode(i, "lower_rank");
			}
			r += cmp;
		}
		Highlights.clearMark(2);
		return r;
	}

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
    	Highlights.defineColor("lower_rank", Color.GREEN);
		for(int i = 0; i < length-1; i++) {
			Highlights.markArray(3, i);

			int t = array[i];
			int r = this.countLesser(array, i, length, t);

			if(r != i) {
				do {
					while(Reads.compareIndexValue(array, r, t, 0.01, true) == 0) r++;

					int t1 = array[r];
					Writes.write(array, r, t, 0.02, false, false);
					
					Highlights.clearAllColors();
					t = t1;
					
					r = this.countLesser(array, i, length, t);
				}
				while(r != i);
				Highlights.clearAllColors();

				Writes.write(array, i, t, 0.02, false, false);
			}
		}
    }
}
